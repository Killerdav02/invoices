package com.amanga.invoices.application.service.payment;

import com.amanga.invoices.application.port.in.payment.SchedulePaymentUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceStatusHistoryRepositoryPort;
import com.amanga.invoices.application.port.out.PaymentScheduleRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.domain.enums.PaymentScheduleStatus;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.InvoiceNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.domain.model.InvoiceStatusHistory;
import com.amanga.invoices.domain.model.PaymentSchedule;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SchedulePaymentService implements SchedulePaymentUseCase {

    private static final Set<InvoiceStatus> SCHEDULABLE_STATUSES = Set.of(
            InvoiceStatus.APROBADA, InvoiceStatus.PROGRAMADA
    );

    private final InvoiceRepositoryPort invoiceRepository;
    private final PaymentScheduleRepositoryPort paymentScheduleRepository;
    private final InvoiceStatusHistoryRepositoryPort statusHistoryRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public SchedulePaymentService(InvoiceRepositoryPort invoiceRepository,
                                  PaymentScheduleRepositoryPort paymentScheduleRepository,
                                  InvoiceStatusHistoryRepositoryPort statusHistoryRepository,
                                  CurrentUserProviderPort currentUserProvider) {
        this.invoiceRepository = invoiceRepository;
        this.paymentScheduleRepository = paymentScheduleRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public PaymentSchedule schedulePayment(PaymentSchedule paymentSchedule) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Find invoice to resolve the company
        Invoice invoice = invoiceRepository.findById(paymentSchedule.getInvoiceId())
                .orElseThrow(() -> new InvoiceNotFoundException(paymentSchedule.getInvoiceId()));

        // 3. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(invoice.getCompanyId())) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), invoice.getCompanyId());
        }

        // 4. Validate current user is ADMIN
        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only admins can schedule payments");
        }

        // 5. Validate invoice is in a schedulable status
        if (!SCHEDULABLE_STATUSES.contains(invoice.getStatus())) {
            throw new ForbiddenActionException(
                    "Cannot schedule payment for invoice " + invoice.getId()
                            + " with status " + invoice.getStatus());
        }

        // 6. Set initial state and creator on the payment schedule
        paymentSchedule.setStatus(PaymentScheduleStatus.PROGRAMADO);
        paymentSchedule.setCreatedByUserId(currentUser.getId());

        // 7. Save the payment schedule
        PaymentSchedule savedSchedule = paymentScheduleRepository.save(paymentSchedule);

        // 8. If invoice was APROBADA, transition it to PROGRAMADA
        if (invoice.getStatus() == InvoiceStatus.APROBADA) {
            invoice.setStatus(InvoiceStatus.PROGRAMADA);
            invoiceRepository.save(invoice);

            statusHistoryRepository.save(InvoiceStatusHistory.builder()
                    .invoiceId(invoice.getId())
                    .changedByUserId(currentUser.getId())
                    .status(InvoiceStatus.PROGRAMADA)
                    .notes("Invoice scheduled for payment")
                    .build());
        }

        return savedSchedule;
    }
}
