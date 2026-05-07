package com.amanga.invoices.application.service.payment;

import com.amanga.invoices.application.port.in.payment.MarkPaymentAsPaidUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceStatusHistoryRepositoryPort;
import com.amanga.invoices.application.port.out.PaymentScheduleRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.domain.enums.PaymentScheduleStatus;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.InvoiceNotFoundException;
import com.amanga.invoices.domain.exception.PaymentScheduleNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.domain.model.InvoiceStatusHistory;
import com.amanga.invoices.domain.model.PaymentSchedule;
import org.springframework.stereotype.Service;

@Service
public class MarkPaymentAsPaidService implements MarkPaymentAsPaidUseCase {

    private final PaymentScheduleRepositoryPort paymentScheduleRepository;
    private final InvoiceRepositoryPort invoiceRepository;
    private final InvoiceStatusHistoryRepositoryPort statusHistoryRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public MarkPaymentAsPaidService(PaymentScheduleRepositoryPort paymentScheduleRepository,
                                    InvoiceRepositoryPort invoiceRepository,
                                    InvoiceStatusHistoryRepositoryPort statusHistoryRepository,
                                    CurrentUserProviderPort currentUserProvider) {
        this.paymentScheduleRepository = paymentScheduleRepository;
        this.invoiceRepository = invoiceRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public PaymentSchedule markPaymentAsPaid(Long paymentScheduleId, Long companyId) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Validate current user is ADMIN
        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only admins can mark payments as paid");
        }

        // 4. Find payment schedule
        PaymentSchedule paymentSchedule = paymentScheduleRepository.findById(paymentScheduleId)
                .orElseThrow(() -> new PaymentScheduleNotFoundException(paymentScheduleId));

        // 5. Find invoice to validate company ownership
        Invoice invoice = invoiceRepository.findById(paymentSchedule.getInvoiceId())
                .orElseThrow(() -> new InvoiceNotFoundException(paymentSchedule.getInvoiceId()));

        // 6. Validate invoice belongs to the company
        if (!invoice.getCompanyId().equals(companyId)) {
            throw new ForbiddenActionException(
                    "Payment schedule " + paymentScheduleId + " does not belong to company " + companyId);
        }

        // 7. Validate payment schedule is in a payable status
        if (paymentSchedule.getStatus() != PaymentScheduleStatus.PROGRAMADO) {
            throw new ForbiddenActionException(
                    "Payment schedule " + paymentScheduleId
                            + " cannot be marked as paid from status " + paymentSchedule.getStatus());
        }

        // 8. Mark payment schedule as paid
        paymentSchedule.setStatus(PaymentScheduleStatus.PAGADO);
        paymentSchedule.setUpdatedByUserId(currentUser.getId());
        PaymentSchedule savedSchedule = paymentScheduleRepository.save(paymentSchedule);

        // 9. If no active scheduled payments remain, transition invoice to PAGADA
        boolean hasActiveSchedules = !paymentScheduleRepository
                .findAllByInvoiceIdAndStatus(invoice.getId(), PaymentScheduleStatus.PROGRAMADO)
                .isEmpty();

        if (!hasActiveSchedules && invoice.getStatus() == InvoiceStatus.PROGRAMADA) {
            invoice.setStatus(InvoiceStatus.PAGADA);
            invoiceRepository.save(invoice);

            statusHistoryRepository.save(InvoiceStatusHistory.builder()
                    .invoiceId(invoice.getId())
                    .changedByUserId(currentUser.getId())
                    .status(InvoiceStatus.PAGADA)
                    .notes("Invoice marked as paid — all payment schedules completed")
                    .build());
        }

        return savedSchedule;
    }
}
