package com.amanga.invoices.infrastructure.adapter.out.persistence.mapper;

import com.amanga.invoices.domain.model.Company;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CompanyPersistenceMapper {

    public Company toDomain(CompanyJpaEntity entity) {
        return Company.builder()
                .id(entity.getId())
                .name(entity.getName())
                .taxId(entity.getTaxId())
                .countryCode(entity.getCountryCode())
                .currency(entity.getCurrency())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public CompanyJpaEntity toEntity(Company domain) {
        CompanyJpaEntity entity = new CompanyJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setTaxId(domain.getTaxId());
        entity.setCountryCode(domain.getCountryCode());
        entity.setCurrency(domain.getCurrency());
        return entity;
    }
}