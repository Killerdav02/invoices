package com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RejectInvoiceRequest {

    @NotBlank(message = "Rejection reason is required")
    @Size(max = 500, message = "Reason must not exceed 500 characters")
    private String reason;

    public String getReason() { return reason; }
}
