package com.amanga.invoices.domain.exception;

public class DuplicateSupplierException extends RuntimeException {

    public DuplicateSupplierException(String taxId) {
        super("Supplier already exists with tax id: " + taxId);
    }
}