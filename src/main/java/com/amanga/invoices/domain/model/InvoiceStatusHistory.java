package com.amanga.invoices.domain.model;

import com.amanga.invoices.domain.enums.InvoiceStatus;
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
public class InvoiceStatusHistory {

    private Long id;
    private Long invoiceId;
    private Long changedByUserId;
    private InvoiceStatus status;
    private LocalDateTime changedAt;
    private String changedBySnapshot;
    private String notes;
}
