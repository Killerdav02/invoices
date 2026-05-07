package com.amanga.invoices.infrastructure.adapter.out.persistence.entity;

import com.amanga.invoices.domain.enums.InvoiceStatus;
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
@Table(name = "invoice_status_history")
public class InvoiceStatusHistoryJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = false)
	private InvoiceJpaEntity invoice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "changed_by_user_id")
	private CompanyUserJpaEntity changedByUser;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private InvoiceStatus status;

	@Column(name = "changed_at", updatable = false)
	private LocalDateTime changedAt;

	@Column(name = "changed_by_snapshot", length = 255)
	private String changedBySnapshot;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	public InvoiceStatusHistoryJpaEntity() {
	}

	@PrePersist
	protected void onCreate() {
		this.changedAt = LocalDateTime.now();
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

	public CompanyUserJpaEntity getChangedByUser() {
		return changedByUser;
	}

	public void setChangedByUser(CompanyUserJpaEntity changedByUser) {
		this.changedByUser = changedByUser;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public void setStatus(InvoiceStatus status) {
		this.status = status;
	}

	public LocalDateTime getChangedAt() {
		return changedAt;
	}

	public String getChangedBySnapshot() {
		return changedBySnapshot;
	}

	public void setChangedBySnapshot(String changedBySnapshot) {
		this.changedBySnapshot = changedBySnapshot;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
