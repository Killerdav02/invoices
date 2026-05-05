package com.amanga.invoices.domain.exception;

public class DuplicateInvoiceException extends RuntimeException {

	public DuplicateInvoiceException(String invoiceNumber, Long supplierId) {
		super("Invoice already exists with number: " + invoiceNumber + " for supplier id: " + supplierId);
	}
}
