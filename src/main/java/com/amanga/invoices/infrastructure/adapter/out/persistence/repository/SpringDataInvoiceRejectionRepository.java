package com.amanga.invoices.infrastructure.adapter.out.persistence.repository;

import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceRejectionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataInvoiceRejectionRepository extends JpaRepository<InvoiceRejectionJpaEntity, Long> {

    List<InvoiceRejectionJpaEntity> findAllByInvoiceId(Long invoiceId);

    Optional<InvoiceRejectionJpaEntity> findByStatusHistoryId(Long statusHistoryId);

    List<InvoiceRejectionJpaEntity> findAllByFeedbackSentFalse();

    Optional<InvoiceRejectionJpaEntity> findFirstByInvoiceIdOrderByCreatedAtDesc(Long invoiceId);
}
