package com.amanga.invoices.infrastructure.adapter.in.web.dto.payment;

import com.amanga.invoices.domain.enums.PaymentPriority;
import com.amanga.invoices.domain.enums.PaymentScheduleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentScheduleResponse {

    private Long id;
    private Long invoiceId;
    private Long createdByUserId;
    private Long updatedByUserId;
    private LocalDate scheduledDate;
    private BigDecimal amount;
    private PaymentPriority priority;
    private PaymentScheduleStatus status;
    private String notes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public PaymentScheduleResponse(Long id, Long invoiceId, Long createdByUserId,
                                   Long updatedByUserId, LocalDate scheduledDate,
                                   BigDecimal amount, PaymentPriority priority,
                                   PaymentScheduleStatus status, String notes,
                                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.scheduledDate = scheduledDate;
        this.amount = amount;
        this.priority = priority;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getInvoiceId() { return invoiceId; }
    public Long getCreatedByUserId() { return createdByUserId; }
    public Long getUpdatedByUserId() { return updatedByUserId; }
    public LocalDate getScheduledDate() { return scheduledDate; }
    public BigDecimal getAmount() { return amount; }
    public PaymentPriority getPriority() { return priority; }
    public PaymentScheduleStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
