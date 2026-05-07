package com.amanga.invoices.infrastructure.adapter.out.persistence.repository;

import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataCompanyRepository extends JpaRepository<CompanyJpaEntity, Long> {

    Optional<CompanyJpaEntity> findByIdAndDeletedAtIsNull(Long id);

    Optional<CompanyJpaEntity> findByTaxId(String taxId);

    List<CompanyJpaEntity> findAllByDeletedAtIsNull();
}
