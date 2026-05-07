package com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreateInvoiceRequest {

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    @NotBlank(message = "Invoice number is required")
    @Size(max = 100, message = "Invoice number must not exceed 100 characters")
    private String invoiceNumber;

    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;

    private LocalDate dueDate;

    @NotNull(message = "Subtotal is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Subtotal must be zero or greater")
    private BigDecimal subTotal;

    @DecimalMin(value = "0.0", inclusive = true, message = "Tax amount must be zero or greater")
    private BigDecimal taxAmount;

    @DecimalMin(value = "0.0", inclusive = true, message = "Discount amount must be zero or greater")
    private BigDecimal discountAmount;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total amount must be zero or greater")
    private BigDecimal totalAmount;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
    private String currency;

    @Size(max = 255, message = "Payment terms must not exceed 255 characters")
    private String paymentTerms;

    private String notes;

    @NotEmpty(message = "At least one line item is required")
    @Valid
    private List<InvoiceLineItemRequest> lineItems;

    @NotNull(message = "Source is required")
    @Valid
    private InvoiceSourceRequest source;

    public Long getSupplierId() { return supplierId; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public BigDecimal getSubTotal() { return subTotal; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getCurrency() { return currency; }
    public String getPaymentTerms() { return paymentTerms; }
    public String getNotes() { return notes; }
    public List<InvoiceLineItemRequest> getLineItems() { return lineItems; }
    public InvoiceSourceRequest getSource() { return source; }
}
