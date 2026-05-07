package com.amanga.invoices.application.port.in.invoice;

import com.amanga.invoices.domain.model.InvoiceStatusHistory;

import java.util.List;

public interface GetInvoiceHistoryUseCase {

    List<InvoiceStatusHistory> getInvoiceHistory(Long invoiceId, Long companyId);
}
