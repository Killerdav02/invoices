package com.amanga.invoices.infrastructure.adapter.out.persistence.repository;

import com.amanga.invoices.domain.enums.CompanyUserRole;
import com.amanga.invoices.domain.enums.CompanyUserStatus;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyUserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataCompanyUserRepository extends JpaRepository<CompanyUserJpaEntity, Long> {

    Optional<CompanyUserJpaEntity> findByAuth0UserId(String auth0UserId);

    Optional<CompanyUserJpaEntity> findByCompanyIdAndAuth0UserId(Long companyId, String auth0UserId);

    Optional<CompanyUserJpaEntity> findByCompanyIdAndEmail(Long companyId, String email);

    List<CompanyUserJpaEntity> findAllByCompanyIdAndDeletedAtIsNull(Long companyId);

    List<CompanyUserJpaEntity> findAllByCompanyIdAndRoleAndDeletedAtIsNull(Long companyId, CompanyUserRole role);

    List<CompanyUserJpaEntity> findAllByCompanyIdAndStatusAndDeletedAtIsNull(Long companyId, CompanyUserStatus status);

    boolean existsByEmailAndDeletedAtIsNull(String email);
}
