package com.amanga.invoices.domain.model;

import com.amanga.invoices.domain.enums.PaymentPriority;
import com.amanga.invoices.domain.enums.PaymentScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSchedule {

    private Long id;
    private Long invoiceId;
    private Long createdByUserId;
    private Long updatedByUserId;
    private LocalDate scheduledDate;
    private BigDecimal amount;
    private PaymentPriority priority;
    private PaymentScheduleStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
