package com.amanga.invoices.application.port.in.payment;

import com.amanga.invoices.domain.model.PaymentSchedule;

import java.util.List;

public interface ListPaymentSchedulesUseCase {

    List<PaymentSchedule> listByInvoice(Long invoiceId, Long companyId);
}
