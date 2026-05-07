package com.amanga.invoices.infrastructure.adapter.out.persistence.repository;

import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceStatusHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataInvoiceStatusHistoryRepository extends JpaRepository<InvoiceStatusHistoryJpaEntity, Long> {

    List<InvoiceStatusHistoryJpaEntity> findAllByInvoiceIdOrderByChangedAtAsc(Long invoiceId);

    List<InvoiceStatusHistoryJpaEntity> findAllByChangedByUserId(Long changedByUserId);
}
