package com.amanga.invoices.infrastructure.adapter.out.persistence.repository;

import com.amanga.invoices.domain.enums.InvoiceFileType;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceFileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataInvoiceFileRepository extends JpaRepository<InvoiceFileJpaEntity, Long> {

    List<InvoiceFileJpaEntity> findAllByInvoiceId(Long invoiceId);

    List<InvoiceFileJpaEntity> findAllByInvoiceIdAndFileType(Long invoiceId, InvoiceFileType fileType);

    List<InvoiceFileJpaEntity> findAllByUploadedByUserId(Long uploadedByUserId);
}
