package com.amanga.invoices.application.port.in.payment;

import com.amanga.invoices.domain.model.PaymentSchedule;

public interface SchedulePaymentUseCase {

    PaymentSchedule schedulePayment(PaymentSchedule paymentSchedule);
}
