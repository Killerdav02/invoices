package com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class InvoiceLineItemRequest {

    @NotBlank(message = "Line item description is required")
    private String description;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
    private BigDecimal quantity;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Unit price must be zero or greater")
    private BigDecimal unitPrice;

    private BigDecimal taxRate;

    @NotNull(message = "Line total is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Line total must be zero or greater")
    private BigDecimal lineTotal;

    public String getDescription() { return description; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getTaxRate() { return taxRate; }
    public BigDecimal getLineTotal() { return lineTotal; }
}
