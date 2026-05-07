package com.amanga.invoices.infrastructure.adapter.in.web.mapper;

import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.domain.model.InvoiceLineItem;
import com.amanga.invoices.domain.model.InvoiceSource;
import com.amanga.invoices.domain.model.InvoiceStatusHistory;
import com.amanga.invoices.domain.model.InvoiceValidation;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.CreateInvoiceRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.InvoiceLineItemRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.InvoiceResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.InvoiceSourceRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.InvoiceStatusHistoryResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.InvoiceValidationResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.RegisterInvoiceSourceRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceWebMapper {

    public Invoice toDomain(CreateInvoiceRequest request, Long companyId) {
        return Invoice.builder()
                .companyId(companyId)
                .supplierId(request.getSupplierId())
                .invoiceNumber(request.getInvoiceNumber())
                .issueDate(request.getIssueDate())
                .dueDate(request.getDueDate())
                .subTotal(request.getSubTotal())
                .taxAmount(request.getTaxAmount())
                .discountAmount(request.getDiscountAmount())
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency())
                .paymentTerms(request.getPaymentTerms())
                .notes(request.getNotes())
                .build();
    }

    public List<InvoiceLineItem> toLineItemDomain(List<InvoiceLineItemRequest> requests) {
        return requests.stream()
                .map(this::toLineItemDomain)
                .collect(Collectors.toList());
    }

    public InvoiceSource toSourceDomain(InvoiceSourceRequest request) {
        return InvoiceSource.builder()
                .sourceType(request.getSourceType())
                .sourceReference(request.getSourceReference())
                .metadata(request.getMetadata())
                .receivedAt(request.getReceivedAt())
                .build();
    }

    public InvoiceSource toSourceDomain(RegisterInvoiceSourceRequest request, Long invoiceId) {
        return InvoiceSource.builder()
                .invoiceId(invoiceId)
                .sourceType(request.getSourceType())
                .sourceReference(request.getSourceReference())
                .metadata(request.getMetadata())
                .receivedAt(request.getReceivedAt())
                .build();
    }

    public InvoiceResponse toResponse(Invoice invoice) {
        return new InvoiceResponse(
                invoice.getId(),
                invoice.getCompanyId(),
                invoice.getSupplierId(),
                invoice.getCreatedByUserId(),
                invoice.getApprovedByUserId(),
                invoice.getInvoiceNumber(),
                invoice.getIssueDate(),
                invoice.getDueDate(),
                invoice.getSubTotal(),
                invoice.getTaxAmount(),
                invoice.getDiscountAmount(),
                invoice.getTotalAmount(),
                invoice.getCurrency(),
                invoice.getStatus(),
                invoice.getPaymentTerms(),
                invoice.getNotes(),
                invoice.getApprovedAt(),
                invoice.getCreatedAt(),
                invoice.getUpdatedAt()
        );
    }

    public InvoiceStatusHistoryResponse toHistoryResponse(InvoiceStatusHistory history) {
        return new InvoiceStatusHistoryResponse(
                history.getId(),
                history.getInvoiceId(),
                history.getChangedByUserId(),
                history.getStatus(),
                history.getChangedAt(),
                history.getChangedBySnapshot(),
                history.getNotes()
        );
    }

    public InvoiceValidationResponse toValidationResponse(InvoiceValidation validation) {
        return new InvoiceValidationResponse(
                validation.getId(),
                validation.getInvoiceId(),
                validation.getValidatedByUserId(),
                validation.getRuleName(),
                validation.getStatus(),
                validation.getMessage(),
                validation.getValidatedAt()
        );
    }

    private InvoiceLineItem toLineItemDomain(InvoiceLineItemRequest request) {
        return InvoiceLineItem.builder()
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .taxRate(request.getTaxRate())
                .lineTotal(request.getLineTotal())
                .build();
    }
}
