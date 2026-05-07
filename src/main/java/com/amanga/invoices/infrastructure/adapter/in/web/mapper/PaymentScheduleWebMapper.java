package com.amanga.invoices.infrastructure.adapter.in.web.mapper;

import com.amanga.invoices.domain.model.PaymentSchedule;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.payment.PaymentScheduleResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.payment.SchedulePaymentRequest;
import org.springframework.stereotype.Component;

@Component
public class PaymentScheduleWebMapper {

    public PaymentSchedule toDomain(SchedulePaymentRequest request, Long invoiceId) {
        return PaymentSchedule.builder()
                .invoiceId(invoiceId)
                .scheduledDate(request.getScheduledDate())
                .amount(request.getAmount())
                .priority(request.getPriority())
                .notes(request.getNotes())
                .build();
    }

    public PaymentScheduleResponse toResponse(PaymentSchedule schedule) {
        return new PaymentScheduleResponse(
                schedule.getId(),
                schedule.getInvoiceId(),
                schedule.getCreatedByUserId(),
                schedule.getUpdatedByUserId(),
                schedule.getScheduledDate(),
                schedule.getAmount(),
                schedule.getPriority(),
                schedule.getStatus(),
                schedule.getNotes(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}
