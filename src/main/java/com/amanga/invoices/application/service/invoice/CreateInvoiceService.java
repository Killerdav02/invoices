package com.amanga.invoices.application.service.invoice;

import com.amanga.invoices.application.port.in.invoice.CreateInvoiceUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.InvoiceLineItemRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceSourceRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceStatusHistoryRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.domain.model.InvoiceLineItem;
import com.amanga.invoices.domain.model.InvoiceSource;
import com.amanga.invoices.domain.model.InvoiceStatusHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateInvoiceService implements CreateInvoiceUseCase {

    private final InvoiceRepositoryPort invoiceRepository;
    private final InvoiceLineItemRepositoryPort lineItemRepository;
    private final InvoiceSourceRepositoryPort invoiceSourceRepository;
    private final InvoiceStatusHistoryRepositoryPort statusHistoryRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public CreateInvoiceService(InvoiceRepositoryPort invoiceRepository,
                                InvoiceLineItemRepositoryPort lineItemRepository,
                                InvoiceSourceRepositoryPort invoiceSourceRepository,
                                InvoiceStatusHistoryRepositoryPort statusHistoryRepository,
                                CurrentUserProviderPort currentUserProvider) {
        this.invoiceRepository = invoiceRepository;
        this.lineItemRepository = lineItemRepository;
        this.invoiceSourceRepository = invoiceSourceRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public Invoice createInvoice(Invoice invoice, List<InvoiceLineItem> lineItems, InvoiceSource source) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(invoice.getCompanyId())) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), invoice.getCompanyId());
        }

        // 3. Check for duplicate invoice number within same supplier and company
        if (invoiceRepository.existsByInvoiceNumberAndSupplierIdAndCompanyId(
                invoice.getInvoiceNumber(), invoice.getSupplierId(), invoice.getCompanyId())) {
            throw new ForbiddenActionException(
                    "Invoice with number " + invoice.getInvoiceNumber() + " already exists for this supplier");
        }

        // 4. Set initial state
        invoice.setStatus(InvoiceStatus.RECIBIDA);
        invoice.setCreatedByUserId(currentUser.getId());

        // 5. Save invoice to get generated ID
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // 6. Persist line items linked to the saved invoice
        List<InvoiceLineItem> itemsWithInvoiceId = lineItems.stream()
                .peek(item -> item.setInvoiceId(savedInvoice.getId()))
                .collect(Collectors.toList());
        lineItemRepository.saveAll(itemsWithInvoiceId);

        // 7. Persist invoice source linked to the saved invoice
        source.setInvoiceId(savedInvoice.getId());
        invoiceSourceRepository.save(source);

        // 8. Record initial status history entry
        InvoiceStatusHistory history = InvoiceStatusHistory.builder()
                .invoiceId(savedInvoice.getId())
                .changedByUserId(currentUser.getId())
                .status(InvoiceStatus.RECIBIDA)
                .notes("Invoice created")
                .build();
        statusHistoryRepository.save(history);

        return savedInvoice;
    }
}
