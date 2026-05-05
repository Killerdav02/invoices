package com.amanga.invoices.domain.exception;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(Long invoiceId) {
        super("Invoice not found with id: " + invoiceId);
    }
}