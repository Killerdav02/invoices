package com.amanga.invoices.domain.model;

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
public class InvoiceRejection {

    private Long id;
    private Long invoiceId;
    private Long statusHistoryId;
    private Long createdByUserId;
    private String reason;
    private Boolean feedbackSent;
    private LocalDateTime feedbackSentAt;
    private LocalDateTime createdAt;
}
