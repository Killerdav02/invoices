package com.amanga.invoices.infrastructure.adapter.out.persistence.repository;

import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceSourceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataInvoiceSourceRepository extends JpaRepository<InvoiceSourceJpaEntity, Long> {

    List<InvoiceSourceJpaEntity> findAllByInvoiceId(Long invoiceId);
}
