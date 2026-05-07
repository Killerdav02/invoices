package com.amanga.invoices.application.port.in.invoice;

import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.domain.model.Invoice;

import java.util.List;

public interface ListInvoicesUseCase {

    List<Invoice> listByCompany(Long companyId, int page, int size);

    List<Invoice> listByStatus(Long companyId, InvoiceStatus status, int page, int size);

    List<Invoice> listBySupplier(Long companyId, Long supplierId, int page, int size);
}
