package com.amanga.invoices.application.port.in.invoice;

import com.amanga.invoices.domain.model.InvoiceRejection;

public interface RejectInvoiceUseCase {

    InvoiceRejection rejectInvoice(Long invoiceId, Long companyId, String reason);
}
