package com.amanga.invoices.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "invoice_rejections")
public class InvoiceRejectionJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = false)
	private InvoiceJpaEntity invoice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_history_id", nullable = false)
	private InvoiceStatusHistoryJpaEntity statusHistory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_user_id")
	private CompanyUserJpaEntity createdByUser;

	@Column(name = "reason", columnDefinition = "TEXT")
	private String reason;

	@Column(name = "feedback_sent")
	private Boolean feedbackSent = false;

	@Column(name = "feedback_sent_at")
	private LocalDateTime feedbackSentAt;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	public InvoiceRejectionJpaEntity() {
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
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

	public InvoiceStatusHistoryJpaEntity getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(InvoiceStatusHistoryJpaEntity statusHistory) {
		this.statusHistory = statusHistory;
	}

	public CompanyUserJpaEntity getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(CompanyUserJpaEntity createdByUser) {
		this.createdByUser = createdByUser;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getFeedbackSent() {
		return feedbackSent;
	}

	public void setFeedbackSent(Boolean feedbackSent) {
		this.feedbackSent = feedbackSent;
	}

	public LocalDateTime getFeedbackSentAt() {
		return feedbackSentAt;
	}

	public void setFeedbackSentAt(LocalDateTime feedbackSentAt) {
		this.feedbackSentAt = feedbackSentAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
