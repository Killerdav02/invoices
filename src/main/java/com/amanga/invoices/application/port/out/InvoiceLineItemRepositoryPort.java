package com.amanga.invoices.application.port.out;

import com.amanga.invoices.domain.model.InvoiceLineItem;

import java.util.List;

public interface InvoiceLineItemRepositoryPort {

    // Persistir todos los items de una factura en una sola operación
    List<InvoiceLineItem> saveAll(List<InvoiceLineItem> lineItems);

    // Listar los items de una factura
    List<InvoiceLineItem> findAllByInvoiceId(Long invoiceId);
}
