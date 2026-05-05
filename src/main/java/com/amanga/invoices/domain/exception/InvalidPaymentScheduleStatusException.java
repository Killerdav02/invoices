package com.amanga.invoices.domain.exception;

import com.amanga.invoices.domain.enums.PaymentScheduleStatus;

public class InvalidPaymentScheduleStatusException extends RuntimeException {

    public InvalidPaymentScheduleStatusException(PaymentScheduleStatus currentStatus, PaymentScheduleStatus targetStatus) {
        super("Invalid payment schedule status transition from " + currentStatus + " to " + targetStatus);
    }
}
