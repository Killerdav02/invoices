package com.amanga.invoices.infrastructure.adapter.in.web.dto.payment;

import com.amanga.invoices.domain.enums.PaymentPriority;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SchedulePaymentRequest {

    @NotNull(message = "Scheduled date is required")
    private LocalDate scheduledDate;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Priority is required")
    private PaymentPriority priority;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    public LocalDate getScheduledDate() { return scheduledDate; }
    public BigDecimal getAmount() { return amount; }
    public PaymentPriority getPriority() { return priority; }
    public String getNotes() { return notes; }
}
