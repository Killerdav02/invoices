package com.amanga.invoices.infrastructure.adapter.out.persistence.repository;

import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceLineItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataInvoiceLineItemRepository extends JpaRepository<InvoiceLineItemJpaEntity, Long> {

    List<InvoiceLineItemJpaEntity> findAllByInvoiceId(Long invoiceId);
}
