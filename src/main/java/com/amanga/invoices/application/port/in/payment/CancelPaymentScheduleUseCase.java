package com.amanga.invoices.application.port.in.payment;

public interface CancelPaymentScheduleUseCase {

    void cancelPaymentSchedule(Long paymentScheduleId, Long companyId);
}
