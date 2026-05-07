package com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice;

import com.amanga.invoices.domain.enums.InvoiceValidationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ValidateInvoiceRequest {

    @NotBlank(message = "Validation rule is required")
    @Size(max = 100, message = "Validation rule must not exceed 100 characters")
    private String validationRule;

    @NotNull(message = "Validation status is required")
    private InvoiceValidationStatus status;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    public String getValidationRule() { return validationRule; }
    public InvoiceValidationStatus getStatus() { return status; }
    public String getNotes() { return notes; }
}
