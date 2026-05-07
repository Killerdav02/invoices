package com.amanga.invoices.infrastructure.adapter.out.persistence.mapper;

import com.amanga.invoices.domain.model.InvoiceStatusHistory;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyUserJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceStatusHistoryJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class InvoiceStatusHistoryPersistenceMapper {

    public InvoiceStatusHistory toDomain(InvoiceStatusHistoryJpaEntity entity) {
        Long changedByUserId = entity.getChangedByUser() != null
                ? entity.getChangedByUser().getId() : null;

        return InvoiceStatusHistory.builder()
                .id(entity.getId())
                .invoiceId(entity.getInvoice().getId())
                .changedByUserId(changedByUserId)
                .status(entity.getStatus())
                .changedAt(entity.getChangedAt())
                .changedBySnapshot(entity.getChangedBySnapshot())
                .notes(entity.getNotes())
                .build();
    }

    public InvoiceStatusHistoryJpaEntity toEntity(InvoiceStatusHistory history) {
        InvoiceJpaEntity invoiceRef = new InvoiceJpaEntity();
        invoiceRef.setId(history.getInvoiceId());

        CompanyUserJpaEntity changedByRef = null;
        if (history.getChangedByUserId() != null) {
            changedByRef = new CompanyUserJpaEntity();
            changedByRef.setId(history.getChangedByUserId());
        }

        InvoiceStatusHistoryJpaEntity entity = new InvoiceStatusHistoryJpaEntity();
        entity.setId(history.getId());
        entity.setInvoice(invoiceRef);
        entity.setChangedByUser(changedByRef);
        entity.setStatus(history.getStatus());
        entity.setChangedBySnapshot(history.getChangedBySnapshot());
        entity.setNotes(history.getNotes());
        return entity;
    }
}
