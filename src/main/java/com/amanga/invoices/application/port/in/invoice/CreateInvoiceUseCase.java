package com.amanga.invoices.application.port.in.invoice;

import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.domain.model.InvoiceLineItem;
import com.amanga.invoices.domain.model.InvoiceSource;

import java.util.List;

public interface CreateInvoiceUseCase {

    Invoice createInvoice(Invoice invoice, List<InvoiceLineItem> lineItems, InvoiceSource source);
}
