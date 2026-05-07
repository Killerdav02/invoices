package com.amanga.invoices.application.port.out;

import com.amanga.invoices.domain.model.InvoiceSource;

import java.util.List;

public interface InvoiceSourceRepositoryPort {

    // Listar todas las fuentes de una factura
    List<InvoiceSource> findAllByInvoiceId(Long invoiceId);

    // Persistir una nueva fuente — inmutable, no se actualiza
    InvoiceSource save(InvoiceSource invoiceSource);
}
