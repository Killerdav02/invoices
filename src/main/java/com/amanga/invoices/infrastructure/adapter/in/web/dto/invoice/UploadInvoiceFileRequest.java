package com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice;

import com.amanga.invoices.domain.enums.InvoiceFileType;
import jakarta.validation.constraints.NotNull;

public class UploadInvoiceFileRequest {

    @NotNull(message = "File type is required")
    private InvoiceFileType fileType;

    public InvoiceFileType getFileType() { return fileType; }
}
