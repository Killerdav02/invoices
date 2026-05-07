package com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice;

import com.amanga.invoices.domain.enums.InvoiceValidationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class InvoiceValidationResponse {

    private Long id;
    private Long invoiceId;
    private Long validatedByUserId;
    private String ruleName;
    private InvoiceValidationStatus status;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validatedAt;

    public InvoiceValidationResponse(Long id, Long invoiceId, Long validatedByUserId,
                                     String ruleName, InvoiceValidationStatus status,
                                     String message, LocalDateTime validatedAt) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.validatedByUserId = validatedByUserId;
        this.ruleName = ruleName;
        this.status = status;
        this.message = message;
        this.validatedAt = validatedAt;
    }

    public Long getId() { return id; }
    public Long getInvoiceId() { return invoiceId; }
    public Long getValidatedByUserId() { return validatedByUserId; }
    public String getRuleName() { return ruleName; }
    public InvoiceValidationStatus getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getValidatedAt() { return validatedAt; }
}
