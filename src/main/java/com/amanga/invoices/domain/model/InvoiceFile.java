package com.amanga.invoices.domain.model;

import com.amanga.invoices.domain.enums.InvoiceFileType;
import com.amanga.invoices.domain.enums.StorageProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceFile {

    private Long id;
    private Long invoiceId;
    private Long uploadedByUserId;
    private InvoiceFileType fileType;
    private StorageProvider storageProvider;
    private String storageBucket;
    private String filePath;
    private Long fileSizeBytes;
    private String checksum;
    private LocalDateTime uploadedAt;
}
