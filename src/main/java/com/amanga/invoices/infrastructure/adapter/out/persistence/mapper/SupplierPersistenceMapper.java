package com.amanga.invoices.infrastructure.adapter.out.persistence.mapper;

import com.amanga.invoices.domain.model.Supplier;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyUserJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.SupplierJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SupplierPersistenceMapper {

    public Supplier toDomain(SupplierJpaEntity entity) {
        Long createdByUserId = entity.getCreatedByUser() != null
                ? entity.getCreatedByUser().getId()
                : null;

        return Supplier.builder()
                .id(entity.getId())
                .companyId(entity.getCompany().getId())
                .createdByUserId(createdByUserId)
                .name(entity.getName())
                .taxId(entity.getTaxId())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .countryCode(entity.getCountryCode())
                .type(entity.getSupplierType())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public SupplierJpaEntity toEntity(Supplier supplier) {
        CompanyJpaEntity companyRef = new CompanyJpaEntity();
        companyRef.setId(supplier.getCompanyId());

        CompanyUserJpaEntity createdByUserRef = null;
        if (supplier.getCreatedByUserId() != null) {
            createdByUserRef = new CompanyUserJpaEntity();
            createdByUserRef.setId(supplier.getCreatedByUserId());
        }

        SupplierJpaEntity entity = new SupplierJpaEntity();
        entity.setId(supplier.getId());
        entity.setCompany(companyRef);
        entity.setCreatedByUser(createdByUserRef);
        entity.setName(supplier.getName());
        entity.setTaxId(supplier.getTaxId());
        entity.setEmail(supplier.getEmail());
        entity.setPhone(supplier.getPhone());
        entity.setCountryCode(supplier.getCountryCode());
        entity.setSupplierType(supplier.getType());
        entity.setDeletedAt(supplier.getDeletedAt());
        return entity;
    }
}
