package com.amanga.invoices.domain.model;

import com.amanga.invoices.domain.enums.InvoiceSourceType;
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
public class InvoiceSource {

    private Long id;
    private Long invoiceId;
    private InvoiceSourceType sourceType;
    private String sourceReference;
    private String metadata;
    private LocalDateTime receivedAt;
}
