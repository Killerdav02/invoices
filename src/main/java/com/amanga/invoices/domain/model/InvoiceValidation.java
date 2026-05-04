package com.amanga.invoices.domain.model;

import com.amanga.invoices.domain.enums.InvoiceValidationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceValidation {

    private Long id;
    private Long invoiceId;
    private Long validatedByUserId;
    private String ruleName;
    private InvoiceValidationStatus status;
    private String message;
    private LocalDateTime validatedAt;
}
