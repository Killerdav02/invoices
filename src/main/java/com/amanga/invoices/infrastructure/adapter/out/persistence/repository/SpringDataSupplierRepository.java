package com.amanga.invoices.infrastructure.adapter.out.persistence.repository;

import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.SupplierJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataSupplierRepository extends JpaRepository<SupplierJpaEntity, Long> {

    Optional<SupplierJpaEntity> findByIdAndDeletedAtIsNull(Long id);

    Optional<SupplierJpaEntity> findByCompanyIdAndTaxId(Long companyId, String taxId);

    List<SupplierJpaEntity> findAllByCompanyIdAndDeletedAtIsNull(Long companyId);
}
