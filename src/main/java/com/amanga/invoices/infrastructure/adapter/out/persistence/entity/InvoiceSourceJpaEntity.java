package com.amanga.invoices.infrastructure.adapter.out.persistence.entity;

import com.amanga.invoices.domain.enums.InvoiceSourceType;
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
@Table(name = "invoice_sources")
public class InvoiceSourceJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = false)
	private InvoiceJpaEntity invoice;

	@Enumerated(EnumType.STRING)
	@Column(name = "source_type", nullable = false)
	private InvoiceSourceType sourceType;

	@Column(name = "source_reference", length = 255)
	private String sourceReference;

	@Column(name = "metadata", columnDefinition = "JSON")
	private String metadata;

	@Column(name = "received_at", updatable = false)
	private LocalDateTime receivedAt;

	public InvoiceSourceJpaEntity() {
	}

	@PrePersist
	protected void onCreate() {
		this.receivedAt = LocalDateTime.now();
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

	public InvoiceSourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(InvoiceSourceType sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceReference() {
		return sourceReference;
	}

	public void setSourceReference(String sourceReference) {
		this.sourceReference = sourceReference;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public LocalDateTime getReceivedAt() {
		return receivedAt;
	}
}
