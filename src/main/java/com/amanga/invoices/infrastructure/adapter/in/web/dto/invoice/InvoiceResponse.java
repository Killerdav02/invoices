package com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice;

import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InvoiceResponse {

    private Long id;
    private Long companyId;
    private Long supplierId;
    private Long createdByUserId;
    private Long approvedByUserId;
    private String invoiceNumber;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal subTotal;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private String currency;
    private InvoiceStatus status;
    private String paymentTerms;
    private String notes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime approvedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public InvoiceResponse(Long id, Long companyId, Long supplierId, Long createdByUserId,
                           Long approvedByUserId, String invoiceNumber, LocalDate issueDate,
                           LocalDate dueDate, BigDecimal subTotal, BigDecimal taxAmount,
                           BigDecimal discountAmount, BigDecimal totalAmount, String currency,
                           InvoiceStatus status, String paymentTerms, String notes,
                           LocalDateTime approvedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.companyId = companyId;
        this.supplierId = supplierId;
        this.createdByUserId = createdByUserId;
        this.approvedByUserId = approvedByUserId;
        this.invoiceNumber = invoiceNumber;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.subTotal = subTotal;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.status = status;
        this.paymentTerms = paymentTerms;
        this.notes = notes;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getCompanyId() { return companyId; }
    public Long getSupplierId() { return supplierId; }
    public Long getCreatedByUserId() { return createdByUserId; }
    public Long getApprovedByUserId() { return approvedByUserId; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public BigDecimal getSubTotal() { return subTotal; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getCurrency() { return currency; }
    public InvoiceStatus getStatus() { return status; }
    public String getPaymentTerms() { return paymentTerms; }
    public String getNotes() { return notes; }
    public LocalDateTime getApprovedAt() { return approvedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
