package com.amanga.invoices.application.port.in.invoice;

import com.amanga.invoices.domain.model.Invoice;

public interface MarkInvoiceAsPaidUseCase {

    Invoice markInvoiceAsPaid(Long invoiceId, Long companyId);
}
