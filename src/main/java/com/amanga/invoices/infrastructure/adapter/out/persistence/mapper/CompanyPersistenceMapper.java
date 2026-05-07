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

    public CompanyJpaEntity toEntity(Company company) {
        CompanyJpaEntity entity = new CompanyJpaEntity();
        entity.setId(company.getId());
        entity.setName(company.getName());
        entity.setTaxId(company.getTaxId());
        entity.setCountryCode(company.getCountryCode());
        entity.setCurrency(company.getCurrency() != null ? company.getCurrency() : "USD");
        entity.setDeletedAt(company.getDeletedAt());
        return entity;
    }
}
