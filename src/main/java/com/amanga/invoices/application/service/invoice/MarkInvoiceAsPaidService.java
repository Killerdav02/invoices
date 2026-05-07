package com.amanga.invoices.application.service.invoice;

import com.amanga.invoices.application.port.in.invoice.MarkInvoiceAsPaidUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceStatusHistoryRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.InvoiceNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.domain.model.InvoiceStatusHistory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MarkInvoiceAsPaidService implements MarkInvoiceAsPaidUseCase {

    private static final Set<InvoiceStatus> PAYABLE_STATUSES = Set.of(
            InvoiceStatus.APROBADA, InvoiceStatus.PROGRAMADA
    );

    private final InvoiceRepositoryPort invoiceRepository;
    private final InvoiceStatusHistoryRepositoryPort statusHistoryRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public MarkInvoiceAsPaidService(InvoiceRepositoryPort invoiceRepository,
                                    InvoiceStatusHistoryRepositoryPort statusHistoryRepository,
                                    CurrentUserProviderPort currentUserProvider) {
        this.invoiceRepository = invoiceRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public Invoice markInvoiceAsPaid(Long invoiceId, Long companyId) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Validate current user is ADMIN
        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only admins can mark invoices as paid");
        }

        // 4. Find invoice
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

        // 5. Validate invoice belongs to the company
        if (!invoice.getCompanyId().equals(companyId)) {
            throw new ForbiddenActionException(
                    "Invoice " + invoiceId + " does not belong to company " + companyId);
        }

        // 6. Validate invoice is in a payable status
        if (!PAYABLE_STATUSES.contains(invoice.getStatus())) {
            throw new ForbiddenActionException(
                    "Invoice " + invoiceId + " cannot be marked as paid from status " + invoice.getStatus());
        }

        // 7. Transition to PAGADA
        invoice.setStatus(InvoiceStatus.PAGADA);

        // 8. Save invoice
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // 9. Record status history
        statusHistoryRepository.save(InvoiceStatusHistory.builder()
                .invoiceId(savedInvoice.getId())
                .changedByUserId(currentUser.getId())
                .status(InvoiceStatus.PAGADA)
                .notes("Invoice marked as paid")
                .build());

        return savedInvoice;
    }
}
