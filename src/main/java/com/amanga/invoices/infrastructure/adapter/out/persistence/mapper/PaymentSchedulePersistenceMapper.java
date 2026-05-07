package com.amanga.invoices.infrastructure.adapter.out.persistence.mapper;

import com.amanga.invoices.domain.model.PaymentSchedule;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyUserJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.PaymentScheduleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentSchedulePersistenceMapper {

    public PaymentSchedule toDomain(PaymentScheduleJpaEntity entity) {
        return PaymentSchedule.builder()
                .id(entity.getId())
                .invoiceId(entity.getInvoice().getId())
                .createdByUserId(entity.getCreatedByUser() != null ? entity.getCreatedByUser().getId() : null)
                .updatedByUserId(entity.getUpdatedByUser() != null ? entity.getUpdatedByUser().getId() : null)
                .scheduledDate(entity.getScheduledDate())
                .amount(entity.getAmount())
                .priority(entity.getPriority())
                .status(entity.getStatus())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public PaymentScheduleJpaEntity toEntity(PaymentSchedule domain) {
        PaymentScheduleJpaEntity entity = new PaymentScheduleJpaEntity();
        entity.setId(domain.getId());

        InvoiceJpaEntity invoice = new InvoiceJpaEntity();
        invoice.setId(domain.getInvoiceId());
        entity.setInvoice(invoice);

        if (domain.getCreatedByUserId() != null) {
            CompanyUserJpaEntity createdByUser = new CompanyUserJpaEntity();
            createdByUser.setId(domain.getCreatedByUserId());
            entity.setCreatedByUser(createdByUser);
        }

        if (domain.getUpdatedByUserId() != null) {
            CompanyUserJpaEntity updatedByUser = new CompanyUserJpaEntity();
            updatedByUser.setId(domain.getUpdatedByUserId());
            entity.setUpdatedByUser(updatedByUser);
        }

        entity.setScheduledDate(domain.getScheduledDate());
        entity.setAmount(domain.getAmount());
        entity.setPriority(domain.getPriority());
        entity.setStatus(domain.getStatus());
        entity.setNotes(domain.getNotes());
        entity.setDeletedAt(domain.getDeletedAt());

        return entity;
    }
}
