package com.amanga.invoices.infrastructure.adapter.out.persistence.mapper;

import com.amanga.invoices.domain.model.CompanyUser;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyUserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CompanyUserPersistenceMapper {

    public CompanyUser toDomain(CompanyUserJpaEntity entity) {
        return CompanyUser.builder()
                .id(entity.getId())
                .companyId(entity.getCompany().getId())
                .auth0UserId(entity.getAuth0UserId())
                .email(entity.getEmail())
                .name(entity.getName())
                .role(entity.getRole())
                .status(entity.getStatus())
                .lastLoginAt(entity.getLastLoginAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public CompanyUserJpaEntity toEntity(CompanyUser companyUser) {
        CompanyJpaEntity companyRef = new CompanyJpaEntity();
        companyRef.setId(companyUser.getCompanyId());

        CompanyUserJpaEntity entity = new CompanyUserJpaEntity();
        entity.setId(companyUser.getId());
        entity.setCompany(companyRef);
        entity.setAuth0UserId(companyUser.getAuth0UserId());
        entity.setEmail(companyUser.getEmail());
        entity.setName(companyUser.getName());
        entity.setRole(companyUser.getRole());
        entity.setStatus(companyUser.getStatus());
        entity.setLastLoginAt(companyUser.getLastLoginAt());
        entity.setDeletedAt(companyUser.getDeletedAt());
        return entity;
    }
}
