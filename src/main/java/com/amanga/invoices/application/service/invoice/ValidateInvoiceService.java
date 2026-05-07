package com.amanga.invoices.application.service.invoice;

import com.amanga.invoices.application.port.in.invoice.ValidateInvoiceUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceStatusHistoryRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceValidationRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.domain.enums.InvoiceValidationStatus;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.InvoiceNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.domain.model.InvoiceStatusHistory;
import com.amanga.invoices.domain.model.InvoiceValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class ValidateInvoiceService implements ValidateInvoiceUseCase {

    private static final Set<InvoiceStatus> VALIDATABLE_STATUSES = Set.of(
            InvoiceStatus.RECIBIDA, InvoiceStatus.EN_REVISION
    );

    private final InvoiceRepositoryPort invoiceRepository;
    private final InvoiceValidationRepositoryPort validationRepository;
    private final InvoiceStatusHistoryRepositoryPort statusHistoryRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public ValidateInvoiceService(InvoiceRepositoryPort invoiceRepository,
                                  InvoiceValidationRepositoryPort validationRepository,
                                  InvoiceStatusHistoryRepositoryPort statusHistoryRepository,
                                  CurrentUserProviderPort currentUserProvider) {
        this.invoiceRepository = invoiceRepository;
        this.validationRepository = validationRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public InvoiceValidation validateInvoice(Long invoiceId,
                                             Long companyId,
                                             Long currentUserId,
                                             String validationRule,
                                             InvoiceValidationStatus status,
                                             String notes) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Validate current user is ADMIN
        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only admins can validate invoices");
        }

        // 4. Find invoice
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

        // 5. Validate invoice belongs to the company
        if (!invoice.getCompanyId().equals(companyId)) {
            throw new ForbiddenActionException(
                    "Invoice " + invoiceId + " does not belong to company " + companyId);
        }

        // 6. Validate invoice is in a validatable status
        if (!VALIDATABLE_STATUSES.contains(invoice.getStatus())) {
            throw new ForbiddenActionException(
                    "Invoice " + invoiceId + " cannot be validated from status " + invoice.getStatus());
        }

        // 7. If invoice is still RECIBIDA, transition it to EN_REVISION
        if (invoice.getStatus() == InvoiceStatus.RECIBIDA) {
            invoice.setStatus(InvoiceStatus.EN_REVISION);
            invoiceRepository.save(invoice);

            statusHistoryRepository.save(InvoiceStatusHistory.builder()
                    .invoiceId(invoiceId)
                    .changedByUserId(currentUser.getId())
                    .status(InvoiceStatus.EN_REVISION)
                    .notes("Invoice moved to review on first validation")
                    .build());
        }

        // 8. Persist the validation record
        return validationRepository.save(InvoiceValidation.builder()
                .invoiceId(invoiceId)
                .validatedByUserId(currentUser.getId())
                .ruleName(validationRule)
                .status(status)
                .message(notes)
                .validatedAt(LocalDateTime.now())
                .build());
    }
}
