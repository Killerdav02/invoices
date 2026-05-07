package com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice;

import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class InvoiceStatusHistoryResponse {

    private Long id;
    private Long invoiceId;
    private Long changedByUserId;
    private InvoiceStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime changedAt;

    private String changedBySnapshot;
    private String notes;

    public InvoiceStatusHistoryResponse(Long id, Long invoiceId, Long changedByUserId,
                                        InvoiceStatus status, LocalDateTime changedAt,
                                        String changedBySnapshot, String notes) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.changedByUserId = changedByUserId;
        this.status = status;
        this.changedAt = changedAt;
        this.changedBySnapshot = changedBySnapshot;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public Long getInvoiceId() { return invoiceId; }
    public Long getChangedByUserId() { return changedByUserId; }
    public InvoiceStatus getStatus() { return status; }
    public LocalDateTime getChangedAt() { return changedAt; }
    public String getChangedBySnapshot() { return changedBySnapshot; }
    public String getNotes() { return notes; }
}
