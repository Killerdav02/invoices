package com.amanga.invoices.application.port.out;

import com.amanga.invoices.domain.model.InvoiceFile;

import java.util.List;
import java.util.Optional;

public interface InvoiceFileRepositoryPort {

    // Devuelve Optional — el Service decide si lanzar la excepción
    Optional<InvoiceFile> findById(Long invoiceFileId);

    // Listar todos los archivos de una factura
    List<InvoiceFile> findAllByInvoiceId(Long invoiceId);

    // Maneja tanto insert como update
    InvoiceFile save(InvoiceFile invoiceFile);

    // Borrado físico — los archivos no usan soft delete
    void delete(Long invoiceFileId);
}
