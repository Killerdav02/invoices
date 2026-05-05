package com.amanga.invoices.domain.exception;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(Long supplierId) {
        super("Supplier not found with id: " + supplierId);
    }
}