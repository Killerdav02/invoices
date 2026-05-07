package com.amanga.invoices.application.port.in.invoice;

import com.amanga.invoices.domain.model.Invoice;

public interface ApproveInvoiceUseCase {

    Invoice approveInvoice(Long invoiceId, Long companyId);
}
