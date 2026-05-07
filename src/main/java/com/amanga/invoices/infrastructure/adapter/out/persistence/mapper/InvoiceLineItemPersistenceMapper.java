package com.amanga.invoices.infrastructure.adapter.out.persistence.mapper;

import com.amanga.invoices.domain.model.InvoiceLineItem;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceLineItemJpaEntity;
import org.springframework.stereotype.Component;
@Component
public class InvoiceLineItemPersistenceMapper {

    public InvoiceLineItem toDomain(InvoiceLineItemJpaEntity entity) {
        return InvoiceLineItem.builder()
                .id(entity.getId())
                .invoiceId(entity.getInvoice().getId())
                .description(entity.getDescription())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .taxRate(entity.getTaxRate())
                .lineTotal(entity.getLineTotal())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public InvoiceLineItemJpaEntity toEntity(InvoiceLineItem domain) {
        InvoiceLineItemJpaEntity entity = new InvoiceLineItemJpaEntity();
        entity.setId(domain.getId());

        InvoiceJpaEntity invoice = new InvoiceJpaEntity();
        invoice.setId(domain.getInvoiceId());
        entity.setInvoice(invoice);

        entity.setDescription(domain.getDescription());
        entity.setQuantity(domain.getQuantity());
        entity.setUnitPrice(domain.getUnitPrice());
        entity.setTaxRate(domain.getTaxRate());
        entity.setLineTotal(domain.getLineTotal());

        return entity;
    }
}
