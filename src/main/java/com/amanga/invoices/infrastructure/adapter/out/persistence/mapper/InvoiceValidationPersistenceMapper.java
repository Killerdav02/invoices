package com.amanga.invoices.infrastructure.adapter.out.persistence.mapper;

import com.amanga.invoices.domain.model.InvoiceValidation;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyUserJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceValidationJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class InvoiceValidationPersistenceMapper {

    public InvoiceValidation toDomain(InvoiceValidationJpaEntity entity) {
        Long validatedByUserId = entity.getValidatedByUser() != null
                ? entity.getValidatedByUser().getId() : null;

        return InvoiceValidation.builder()
                .id(entity.getId())
                .invoiceId(entity.getInvoice().getId())
                .validatedByUserId(validatedByUserId)
                .ruleName(entity.getRuleName())
                .status(entity.getStatus())
                .message(entity.getMessage())
                .validatedAt(entity.getValidatedAt())
                .build();
    }

    public InvoiceValidationJpaEntity toEntity(InvoiceValidation validation) {
        InvoiceJpaEntity invoiceRef = new InvoiceJpaEntity();
        invoiceRef.setId(validation.getInvoiceId());

        CompanyUserJpaEntity validatedByRef = null;
        if (validation.getValidatedByUserId() != null) {
            validatedByRef = new CompanyUserJpaEntity();
            validatedByRef.setId(validation.getValidatedByUserId());
        }

        InvoiceValidationJpaEntity entity = new InvoiceValidationJpaEntity();
        entity.setId(validation.getId());
        entity.setInvoice(invoiceRef);
        entity.setValidatedByUser(validatedByRef);
        entity.setRuleName(validation.getRuleName());
        entity.setStatus(validation.getStatus());
        entity.setMessage(validation.getMessage());
        return entity;
    }
}
