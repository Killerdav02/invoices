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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice_line_items")
public class InvoiceLineItemJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = false)
	private InvoiceJpaEntity invoice;

	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "quantity", nullable = false, precision = 10, scale = 4)
	private BigDecimal quantity = BigDecimal.ONE;

	@Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
	private BigDecimal unitPrice = BigDecimal.ZERO;

	@Column(name = "tax_rate", nullable = false, precision = 5, scale = 2)
	private BigDecimal taxRate = BigDecimal.ZERO;

	@Column(name = "line_total", nullable = false, precision = 15, scale = 2)
	private BigDecimal lineTotal = BigDecimal.ZERO;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	public InvoiceLineItemJpaEntity() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
