package com.amanga.invoices.infrastructure.adapter.out.persistence.repository;

import com.amanga.invoices.domain.enums.InvoiceValidationStatus;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceValidationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataInvoiceValidationRepository extends JpaRepository<InvoiceValidationJpaEntity, Long> {

    List<InvoiceValidationJpaEntity> findAllByInvoiceId(Long invoiceId);

    List<InvoiceValidationJpaEntity> findAllByInvoiceIdAndStatus(Long invoiceId, InvoiceValidationStatus status);

    List<InvoiceValidationJpaEntity> findAllByValidatedByUserId(Long validatedByUserId);

    boolean existsByInvoiceIdAndStatus(Long invoiceId, InvoiceValidationStatus status);
}
