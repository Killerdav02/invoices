package com.amanga.invoices.infrastructure.adapter.out.persistence.entity;

import com.amanga.invoices.domain.enums.InvoiceFileType;
import com.amanga.invoices.domain.enums.StorageProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoice_files")
public class InvoiceFileJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = false)
	private InvoiceJpaEntity invoice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uploaded_by_user_id")
	private CompanyUserJpaEntity uploadedByUser;

	@Enumerated(EnumType.STRING)
	@Column(name = "file_type", nullable = false)
	private InvoiceFileType fileType;

	@Enumerated(EnumType.STRING)
	@Column(name = "storage_provider", nullable = false)
	private StorageProvider storageProvider = StorageProvider.S3;

	@Column(name = "storage_bucket", length = 255)
	private String storageBucket;

	@Column(name = "file_path", nullable = false, length = 1000)
	private String filePath;

	@Column(name = "file_size_bytes")
	private Long fileSizeBytes;

	@Column(name = "checksum", length = 64)
	private String checksum;

	@Column(name = "uploaded_at", updatable = false)
	private LocalDateTime uploadedAt;

	public InvoiceFileJpaEntity() {
	}

	@PrePersist
	protected void onCreate() {
		this.uploadedAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InvoiceJpaEntity getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceJpaEntity invoice) {
		this.invoice = invoice;
	}

	public CompanyUserJpaEntity getUploadedByUser() {
		return uploadedByUser;
	}

	public void setUploadedByUser(CompanyUserJpaEntity uploadedByUser) {
		this.uploadedByUser = uploadedByUser;
	}

	public InvoiceFileType getFileType() {
		return fileType;
	}

	public void setFileType(InvoiceFileType fileType) {
		this.fileType = fileType;
	}

	public StorageProvider getStorageProvider() {
		return storageProvider;
	}

	public void setStorageProvider(StorageProvider storageProvider) {
		this.storageProvider = storageProvider;
	}

	public String getStorageBucket() {
		return storageBucket;
	}

	public void setStorageBucket(String storageBucket) {
		this.storageBucket = storageBucket;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getFileSizeBytes() {
		return fileSizeBytes;
	}

	public void setFileSizeBytes(Long fileSizeBytes) {
		this.fileSizeBytes = fileSizeBytes;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public LocalDateTime getUploadedAt() {
		return uploadedAt;
	}
}
