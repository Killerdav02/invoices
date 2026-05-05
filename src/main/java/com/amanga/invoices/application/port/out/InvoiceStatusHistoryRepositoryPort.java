package com.amanga.invoices.application.port.out;

import com.amanga.invoices.domain.model.InvoiceStatusHistory;

import java.util.List;

public interface InvoiceStatusHistoryRepositoryPort {

    // Listar todo el historial de una factura ordenado cronológicamente
    List<InvoiceStatusHistory> findAllByInvoiceId(Long invoiceId);

    // Persistir una nueva entrada — el historial es inmutable, no se actualiza
    InvoiceStatusHistory save(InvoiceStatusHistory invoiceStatusHistory);
}
