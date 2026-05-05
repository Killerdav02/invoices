package com.amanga.invoices.domain.exception;

public class PaymentScheduleNotFoundException extends RuntimeException {
    public PaymentScheduleNotFoundException(Long paymentScheduleId) {
        super("Payment schedule not found with id: " + paymentScheduleId);
    }
}