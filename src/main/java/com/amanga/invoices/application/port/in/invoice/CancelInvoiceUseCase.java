package com.amanga.invoices.application.port.in.invoice;

import com.amanga.invoices.domain.model.Invoice;

public interface CancelInvoiceUseCase {

    Invoice cancelInvoice(Long invoiceId, Long companyId);
}
