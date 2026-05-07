package com.amanga.invoices.application.port.in.invoice;

import com.amanga.invoices.domain.enums.InvoiceFileType;
import com.amanga.invoices.domain.model.InvoiceFile;

public interface UploadInvoiceFileUseCase {

    InvoiceFile uploadInvoiceFile(
            Long invoiceId,
            Long companyId,
            Long currentUserId,
            byte[] content,
            String fileName,
            String contentType,
            InvoiceFileType fileType);
}
