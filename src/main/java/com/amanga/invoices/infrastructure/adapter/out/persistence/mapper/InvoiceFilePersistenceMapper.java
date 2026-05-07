package com.amanga.invoices.infrastructure.adapter.out.persistence.mapper;

import com.amanga.invoices.domain.model.InvoiceFile;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyUserJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceFileJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class InvoiceFilePersistenceMapper {

    public InvoiceFile toDomain(InvoiceFileJpaEntity entity) {
        Long uploadedByUserId = entity.getUploadedByUser() != null
                ? entity.getUploadedByUser().getId() : null;

        return InvoiceFile.builder()
                .id(entity.getId())
                .invoiceId(entity.getInvoice().getId())
                .uploadedByUserId(uploadedByUserId)
                .fileType(entity.getFileType())
                .storageProvider(entity.getStorageProvider())
                .storageBucket(entity.getStorageBucket())
                .filePath(entity.getFilePath())
                .fileSizeBytes(entity.getFileSizeBytes())
                .checksum(entity.getChecksum())
                .uploadedAt(entity.getUploadedAt())
                .build();
    }

    public InvoiceFileJpaEntity toEntity(InvoiceFile invoiceFile) {
        InvoiceJpaEntity invoiceRef = new InvoiceJpaEntity();
        invoiceRef.setId(invoiceFile.getInvoiceId());

        CompanyUserJpaEntity uploadedByRef = null;
        if (invoiceFile.getUploadedByUserId() != null) {
            uploadedByRef = new CompanyUserJpaEntity();
            uploadedByRef.setId(invoiceFile.getUploadedByUserId());
        }

        InvoiceFileJpaEntity entity = new InvoiceFileJpaEntity();
        entity.setId(invoiceFile.getId());
        entity.setInvoice(invoiceRef);
        entity.setUploadedByUser(uploadedByRef);
        entity.setFileType(invoiceFile.getFileType());
        entity.setStorageProvider(invoiceFile.getStorageProvider());
        entity.setStorageBucket(invoiceFile.getStorageBucket());
        entity.setFilePath(invoiceFile.getFilePath());
        entity.setFileSizeBytes(invoiceFile.getFileSizeBytes());
        entity.setChecksum(invoiceFile.getChecksum());
        return entity;
    }
}
