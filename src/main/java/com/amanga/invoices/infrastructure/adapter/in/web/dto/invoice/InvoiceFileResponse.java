package com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice;

import com.amanga.invoices.domain.enums.InvoiceFileType;
import com.amanga.invoices.domain.enums.StorageProvider;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class InvoiceFileResponse {

    private Long id;
    private Long invoiceId;
    private Long uploadedByUserId;
    private InvoiceFileType fileType;
    private StorageProvider storageProvider;
    private String storageBucket;
    private String filePath;
    private Long fileSizeBytes;
    private String checksum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime uploadedAt;

    public InvoiceFileResponse(Long id, Long invoiceId, Long uploadedByUserId,
                               InvoiceFileType fileType, StorageProvider storageProvider,
                               String storageBucket, String filePath,
                               Long fileSizeBytes, String checksum,
                               LocalDateTime uploadedAt) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.uploadedByUserId = uploadedByUserId;
        this.fileType = fileType;
        this.storageProvider = storageProvider;
        this.storageBucket = storageBucket;
        this.filePath = filePath;
        this.fileSizeBytes = fileSizeBytes;
        this.checksum = checksum;
        this.uploadedAt = uploadedAt;
    }

    public Long getId() { return id; }
    public Long getInvoiceId() { return invoiceId; }
    public Long getUploadedByUserId() { return uploadedByUserId; }
    public InvoiceFileType getFileType() { return fileType; }
    public StorageProvider getStorageProvider() { return storageProvider; }
    public String getStorageBucket() { return storageBucket; }
    public String getFilePath() { return filePath; }
    public Long getFileSizeBytes() { return fileSizeBytes; }
    public String getChecksum() { return checksum; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
}
