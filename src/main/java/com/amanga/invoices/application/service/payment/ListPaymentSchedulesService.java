package com.amanga.invoices.application.service.payment;

import com.amanga.invoices.application.port.in.payment.ListPaymentSchedulesUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.application.port.out.PaymentScheduleRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.InvoiceNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.domain.model.PaymentSchedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListPaymentSchedulesService implements ListPaymentSchedulesUseCase {

    private final PaymentScheduleRepositoryPort paymentScheduleRepository;
    private final InvoiceRepositoryPort invoiceRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public ListPaymentSchedulesService(PaymentScheduleRepositoryPort paymentScheduleRepository,
                                       InvoiceRepositoryPort invoiceRepository,
                                       CurrentUserProviderPort currentUserProvider) {
        this.paymentScheduleRepository = paymentScheduleRepository;
        this.invoiceRepository = invoiceRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public List<PaymentSchedule> listByInvoice(Long invoiceId, Long companyId) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Find invoice
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

        // 4. Validate invoice belongs to the company
        if (!invoice.getCompanyId().equals(companyId)) {
            throw new ForbiddenActionException(
                    "Invoice " + invoiceId + " does not belong to company " + companyId);
        }

        // 5. Return all payment schedules for the invoice
        return paymentScheduleRepository.findAllByInvoiceId(invoiceId);
    }
}
