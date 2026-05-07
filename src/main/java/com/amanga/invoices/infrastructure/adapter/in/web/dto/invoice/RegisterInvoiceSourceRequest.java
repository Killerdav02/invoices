package com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice;

import com.amanga.invoices.domain.enums.InvoiceSourceType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class RegisterInvoiceSourceRequest {

    @NotNull(message = "Source type is required")
    private InvoiceSourceType sourceType;

    private String sourceReference;

    private String metadata;

    private LocalDateTime receivedAt;

    public InvoiceSourceType getSourceType() { return sourceType; }
    public String getSourceReference() { return sourceReference; }
    public String getMetadata() { return metadata; }
    public LocalDateTime getReceivedAt() { return receivedAt; }
}
