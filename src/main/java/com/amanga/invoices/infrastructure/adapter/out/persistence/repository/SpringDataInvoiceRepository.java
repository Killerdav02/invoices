package com.amanga.invoices.infrastructure.adapter.out.persistence.repository;

import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataInvoiceRepository extends JpaRepository<InvoiceJpaEntity, Long> {

    Optional<InvoiceJpaEntity> findByIdAndDeletedAtIsNull(Long id);

    Optional<InvoiceJpaEntity> findByIdAndCompanyIdAndDeletedAtIsNull(Long id, Long companyId);

    Optional<InvoiceJpaEntity> findByCompanyIdAndSupplierIdAndInvoiceNumber(
            Long companyId, Long supplierId, String invoiceNumber);

    Page<InvoiceJpaEntity> findAllByCompanyIdAndDeletedAtIsNull(Long companyId, Pageable pageable);

    Page<InvoiceJpaEntity> findAllByCompanyIdAndCurrentStatusAndDeletedAtIsNull(
            Long companyId, InvoiceStatus currentStatus, Pageable pageable);

    Page<InvoiceJpaEntity> findAllByCompanyIdAndSupplierIdAndDeletedAtIsNull(
            Long companyId, Long supplierId, Pageable pageable);

    List<InvoiceJpaEntity> findAllByCompanyIdAndDueDateBeforeAndDeletedAtIsNull(
            Long companyId, LocalDate date);

    List<InvoiceJpaEntity> findAllBySupplierIdAndDeletedAtIsNull(Long supplierId);
}

