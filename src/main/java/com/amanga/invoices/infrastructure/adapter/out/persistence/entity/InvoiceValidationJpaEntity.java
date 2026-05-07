package com.amanga.invoices.infrastructure.adapter.out.persistence.entity;

import com.amanga.invoices.domain.enums.InvoiceValidationStatus;
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
@Table(name = "invoice_validations")
public class InvoiceValidationJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = false)
	private InvoiceJpaEntity invoice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "validated_by_user_id")
	private CompanyUserJpaEntity validatedByUser;

	@Column(name = "rule_name", nullable = false, length = 100)
	private String ruleName;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private InvoiceValidationStatus status = InvoiceValidationStatus.PENDIENTE;

	@Column(name = "message", columnDefinition = "TEXT")
	private String message;

	@Column(name = "validated_at", updatable = false)
	private LocalDateTime validatedAt;

	public InvoiceValidationJpaEntity() {
	}

	@PrePersist
	protected void onCreate() {
		this.validatedAt = LocalDateTime.now();
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

	public CompanyUserJpaEntity getValidatedByUser() {
		return validatedByUser;
	}

	public void setValidatedByUser(CompanyUserJpaEntity validatedByUser) {
		this.validatedByUser = validatedByUser;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public InvoiceValidationStatus getStatus() {
		return status;
	}

	public void setStatus(InvoiceValidationStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getValidatedAt() {
		return validatedAt;
	}
}
