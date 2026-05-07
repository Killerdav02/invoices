package com.amanga.invoices.application.port.in.invoice;

import com.amanga.invoices.domain.model.InvoiceSource;

public interface RegisterInvoiceSourceUseCase {

    InvoiceSource registerInvoiceSource(InvoiceSource invoiceSource);
}
