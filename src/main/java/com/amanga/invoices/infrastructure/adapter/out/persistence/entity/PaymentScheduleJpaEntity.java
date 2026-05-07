package com.amanga.invoices.infrastructure.adapter.out.persistence.entity;

import com.amanga.invoices.domain.enums.PaymentPriority;
import com.amanga.invoices.domain.enums.PaymentScheduleStatus;
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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_schedules")
public class PaymentScheduleJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = false)
	private InvoiceJpaEntity invoice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_user_id")
	private CompanyUserJpaEntity createdByUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by_user_id")
	private CompanyUserJpaEntity updatedByUser;

	@Column(name = "scheduled_date", nullable = false)
	private LocalDate scheduledDate;

	@Column(name = "amount", nullable = false, precision = 15, scale = 2)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "priority")
	private PaymentPriority priority = PaymentPriority.MEDIA;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private PaymentScheduleStatus status = PaymentScheduleStatus.PENDIENTE;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	public PaymentScheduleJpaEntity() {
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
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

	public CompanyUserJpaEntity getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(CompanyUserJpaEntity createdByUser) {
		this.createdByUser = createdByUser;
	}

	public CompanyUserJpaEntity getUpdatedByUser() {
		return updatedByUser;
	}

	public void setUpdatedByUser(CompanyUserJpaEntity updatedByUser) {
		this.updatedByUser = updatedByUser;
	}

	public LocalDate getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(LocalDate scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public PaymentPriority getPriority() {
		return priority;
	}

	public void setPriority(PaymentPriority priority) {
		this.priority = priority;
	}

	public PaymentScheduleStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentScheduleStatus status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}
}
