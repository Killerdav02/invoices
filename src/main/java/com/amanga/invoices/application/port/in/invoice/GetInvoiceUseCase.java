package com.amanga.invoices.application.port.in.invoice;

import com.amanga.invoices.domain.model.Invoice;

public interface GetInvoiceUseCase {

    Invoice getInvoice(Long invoiceId, Long companyId);
}
