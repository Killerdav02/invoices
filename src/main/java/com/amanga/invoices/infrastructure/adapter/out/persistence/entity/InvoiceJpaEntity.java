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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
	name = "invoices",
	uniqueConstraints = {
		@UniqueConstraint(name = "uq_factura_proveedor", columnNames = { "company_id", "supplier_id", "invoice_number" })
	}
)
public class InvoiceJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private CompanyJpaEntity company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id", nullable = false)
	private SupplierJpaEntity supplier;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_user_id")
	private CompanyUserJpaEntity createdByUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approved_by_user_id")
	private CompanyUserJpaEntity approvedByUser;

	@Column(name = "invoice_number", nullable = false, length = 100)
	private String invoiceNumber;

	@Column(name = "issue_date", nullable = false)
	private LocalDate issueDate;

	@Column(name = "due_date")
	private LocalDate dueDate;

	@Column(name = "subtotal", nullable = false, precision = 15, scale = 2)
	private BigDecimal subtotal = BigDecimal.ZERO;

	@Column(name = "tax_amount", nullable = false, precision = 15, scale = 2)
	private BigDecimal taxAmount = BigDecimal.ZERO;

	@Column(name = "discount_amount", nullable = false, precision = 15, scale = 2)
	private BigDecimal discountAmount = BigDecimal.ZERO;

	@Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
	private BigDecimal totalAmount = BigDecimal.ZERO;

	@Column(name = "currency", nullable = false, length = 3)
	private String currency = "USD";

	@Enumerated(EnumType.STRING)
	@Column(name = "current_status", nullable = false)
	private InvoiceStatus currentStatus = InvoiceStatus.RECIBIDA;

	@Column(name = "payment_terms", length = 50)
	private String paymentTerms;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@Column(name = "approved_at")
	private LocalDateTime approvedAt;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	public InvoiceJpaEntity() {
	}

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
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

	public CompanyJpaEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyJpaEntity company) {
		this.company = company;
	}

	public SupplierJpaEntity getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierJpaEntity supplier) {
		this.supplier = supplier;
	}

	public CompanyUserJpaEntity getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(CompanyUserJpaEntity createdByUser) {
		this.createdByUser = createdByUser;
	}

	public CompanyUserJpaEntity getApprovedByUser() {
		return approvedByUser;
	}

	public void setApprovedByUser(CompanyUserJpaEntity approvedByUser) {
		this.approvedByUser = approvedByUser;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public InvoiceStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(InvoiceStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(LocalDateTime approvedAt) {
		this.approvedAt = approvedAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}
}
