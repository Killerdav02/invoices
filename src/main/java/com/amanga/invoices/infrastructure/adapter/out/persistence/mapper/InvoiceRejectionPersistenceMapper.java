package com.amanga.invoices.infrastructure.adapter.out.persistence.mapper;

import com.amanga.invoices.domain.model.InvoiceRejection;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyUserJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceRejectionJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceStatusHistoryJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class InvoiceRejectionPersistenceMapper {

    public InvoiceRejection toDomain(InvoiceRejectionJpaEntity entity) {
        Long createdByUserId = entity.getCreatedByUser() != null
                ? entity.getCreatedByUser().getId() : null;

        return InvoiceRejection.builder()
                .id(entity.getId())
                .invoiceId(entity.getInvoice().getId())
                .statusHistoryId(entity.getStatusHistory().getId())
                .createdByUserId(createdByUserId)
                .reason(entity.getReason())
                .feedbackSent(entity.getFeedbackSent())
                .feedbackSentAt(entity.getFeedbackSentAt())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public InvoiceRejectionJpaEntity toEntity(InvoiceRejection rejection) {
        InvoiceJpaEntity invoiceRef = new InvoiceJpaEntity();
        invoiceRef.setId(rejection.getInvoiceId());

        InvoiceStatusHistoryJpaEntity statusHistoryRef = new InvoiceStatusHistoryJpaEntity();
        statusHistoryRef.setId(rejection.getStatusHistoryId());

        CompanyUserJpaEntity createdByRef = null;
        if (rejection.getCreatedByUserId() != null) {
            createdByRef = new CompanyUserJpaEntity();
            createdByRef.setId(rejection.getCreatedByUserId());
        }

        InvoiceRejectionJpaEntity entity = new InvoiceRejectionJpaEntity();
        entity.setId(rejection.getId());
        entity.setInvoice(invoiceRef);
        entity.setStatusHistory(statusHistoryRef);
        entity.setCreatedByUser(createdByRef);
        entity.setReason(rejection.getReason());
        entity.setFeedbackSent(rejection.getFeedbackSent());
        entity.setFeedbackSentAt(rejection.getFeedbackSentAt());
        return entity;
    }
}
