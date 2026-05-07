package com.amanga.invoices.application.port.in.payment;

import com.amanga.invoices.domain.model.PaymentSchedule;

public interface MarkPaymentAsPaidUseCase {

    PaymentSchedule markPaymentAsPaid(Long paymentScheduleId, Long companyId);
}
