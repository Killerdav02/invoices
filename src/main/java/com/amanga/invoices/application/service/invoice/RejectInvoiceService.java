package com.amanga.invoices.application.service.invoice;

import com.amanga.invoices.application.port.in.invoice.RejectInvoiceUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.InvoiceRejectionRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceStatusHistoryRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.InvoiceNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.domain.model.InvoiceRejection;
import com.amanga.invoices.domain.model.InvoiceStatusHistory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RejectInvoiceService implements RejectInvoiceUseCase {

    private static final Set<InvoiceStatus> REJECTABLE_STATUSES = Set.of(
            InvoiceStatus.RECIBIDA, InvoiceStatus.EN_REVISION
    );

    private final InvoiceRepositoryPort invoiceRepository;
    private final InvoiceStatusHistoryRepositoryPort statusHistoryRepository;
    private final InvoiceRejectionRepositoryPort rejectionRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public RejectInvoiceService(InvoiceRepositoryPort invoiceRepository,
                                InvoiceStatusHistoryRepositoryPort statusHistoryRepository,
                                InvoiceRejectionRepositoryPort rejectionRepository,
                                CurrentUserProviderPort currentUserProvider) {
        this.invoiceRepository = invoiceRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.rejectionRepository = rejectionRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public InvoiceRejection rejectInvoice(Long invoiceId, Long companyId, String reason) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Validate current user is ADMIN
        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only admins can reject invoices");
        }

        // 4. Find invoice
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

        // 5. Validate invoice belongs to the company
        if (!invoice.getCompanyId().equals(companyId)) {
            throw new ForbiddenActionException(
                    "Invoice " + invoiceId + " does not belong to company " + companyId);
        }

        // 6. Validate invoice is in a rejectable status
        if (!REJECTABLE_STATUSES.contains(invoice.getStatus())) {
            throw new ForbiddenActionException(
                    "Invoice " + invoiceId + " cannot be rejected from status " + invoice.getStatus());
        }

        // 7. Transition to RECHAZADA
        invoice.setStatus(InvoiceStatus.RECHAZADA);
        invoiceRepository.save(invoice);

        // 8. Record status history
        InvoiceStatusHistory history = statusHistoryRepository.save(InvoiceStatusHistory.builder()
                .invoiceId(invoiceId)
                .changedByUserId(currentUser.getId())
                .status(InvoiceStatus.RECHAZADA)
                .notes(reason)
                .build());

        // 9. Persist rejection record linked to the history entry
        return rejectionRepository.save(InvoiceRejection.builder()
                .invoiceId(invoiceId)
                .statusHistoryId(history.getId())
                .createdByUserId(currentUser.getId())
                .reason(reason)
                .feedbackSent(false)
                .build());
    }
}
