package com.amanga.invoices.application.port.out;

import com.amanga.invoices.domain.enums.InvoiceValidationStatus;
import com.amanga.invoices.domain.model.InvoiceValidation;

import java.util.List;

public interface InvoiceValidationRepositoryPort {

    // Listar todas las validaciones de una factura
    List<InvoiceValidation> findAllByInvoiceId(Long invoiceId);

    // Listar validaciones de una factura por estado
    List<InvoiceValidation> findAllByInvoiceIdAndStatus(Long invoiceId, InvoiceValidationStatus status);

    // Persistir una nueva validación — las validaciones no se actualizan
    InvoiceValidation save(InvoiceValidation invoiceValidation);

    // Verificar si una factura tiene alguna validación fallida
    boolean existsFailedByInvoiceId(Long invoiceId);
}
