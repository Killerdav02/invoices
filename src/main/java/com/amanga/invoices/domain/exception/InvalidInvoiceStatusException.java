package com.amanga.invoices.domain.exception;

import com.amanga.invoices.domain.enums.InvoiceStatus;

public class InvalidInvoiceStatusException extends RuntimeException {

	public InvalidInvoiceStatusException(InvoiceStatus currentStatus, InvoiceStatus targetStatus) {
		super("Invalid invoice status transition from " + currentStatus + " to " + targetStatus);
	}
}
