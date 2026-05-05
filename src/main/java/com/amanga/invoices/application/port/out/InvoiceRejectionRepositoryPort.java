package com.amanga.invoices.application.port.out;

import com.amanga.invoices.domain.model.InvoiceRejection;

import java.util.Optional;

public interface InvoiceRejectionRepositoryPort {

    // Devuelve Optional — útil para saber si una factura fue rechazada
    Optional<InvoiceRejection> findByInvoiceId(Long invoiceId);

    // Persistir un nuevo rechazo — es inmutable, no se actualiza
    InvoiceRejection save(InvoiceRejection invoiceRejection);

    // Actualizar solo el estado de feedback enviado
    void markFeedbackSent(Long invoiceRejectionId);
}
