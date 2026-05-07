package com.amanga.invoices.infrastructure.adapter.out.persistence.mapper;

import com.amanga.invoices.domain.model.InvoiceSource;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceSourceJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class InvoiceSourcePersistenceMapper {

    public InvoiceSource toDomain(InvoiceSourceJpaEntity entity) {
        return InvoiceSource.builder()
                .id(entity.getId())
                .invoiceId(entity.getInvoice().getId())
                .sourceType(entity.getSourceType())
                .sourceReference(entity.getSourceReference())
                .metadata(entity.getMetadata())
                .receivedAt(entity.getReceivedAt())
                .build();
    }

    public InvoiceSourceJpaEntity toEntity(InvoiceSource source) {
        InvoiceJpaEntity invoiceRef = new InvoiceJpaEntity();
        invoiceRef.setId(source.getInvoiceId());

        InvoiceSourceJpaEntity entity = new InvoiceSourceJpaEntity();
        entity.setId(source.getId());
        entity.setInvoice(invoiceRef);
        entity.setSourceType(source.getSourceType());
        entity.setSourceReference(source.getSourceReference());
        entity.setMetadata(source.getMetadata());
        return entity;
    }
}
