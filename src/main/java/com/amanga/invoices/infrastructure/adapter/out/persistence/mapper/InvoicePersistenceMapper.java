package com.amanga.invoices.infrastructure.adapter.out.persistence.mapper;

import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.CompanyUserJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.InvoiceJpaEntity;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.SupplierJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class InvoicePersistenceMapper {

    public Invoice toDomain(InvoiceJpaEntity entity) {
        Long createdByUserId = entity.getCreatedByUser() != null
                ? entity.getCreatedByUser().getId() : null;
        Long approvedByUserId = entity.getApprovedByUser() != null
                ? entity.getApprovedByUser().getId() : null;

        return Invoice.builder()
                .id(entity.getId())
                .companyId(entity.getCompany().getId())
                .supplierId(entity.getSupplier().getId())
                .createdByUserId(createdByUserId)
                .approvedByUserId(approvedByUserId)
                .invoiceNumber(entity.getInvoiceNumber())
                .issueDate(entity.getIssueDate())
                .dueDate(entity.getDueDate())
                .subTotal(entity.getSubtotal())
                .taxAmount(entity.getTaxAmount())
                .discountAmount(entity.getDiscountAmount())
                .totalAmount(entity.getTotalAmount())
                .currency(entity.getCurrency())
                .status(entity.getCurrentStatus())
                .paymentTerms(entity.getPaymentTerms())
                .notes(entity.getNotes())
                .approvedAt(entity.getApprovedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public InvoiceJpaEntity toEntity(Invoice invoice) {
        CompanyJpaEntity companyRef = new CompanyJpaEntity();
        companyRef.setId(invoice.getCompanyId());

        SupplierJpaEntity supplierRef = new SupplierJpaEntity();
        supplierRef.setId(invoice.getSupplierId());

        CompanyUserJpaEntity createdByRef = null;
        if (invoice.getCreatedByUserId() != null) {
            createdByRef = new CompanyUserJpaEntity();
            createdByRef.setId(invoice.getCreatedByUserId());
        }

        CompanyUserJpaEntity approvedByRef = null;
        if (invoice.getApprovedByUserId() != null) {
            approvedByRef = new CompanyUserJpaEntity();
            approvedByRef.setId(invoice.getApprovedByUserId());
        }

        InvoiceJpaEntity entity = new InvoiceJpaEntity();
        entity.setId(invoice.getId());
        entity.setCompany(companyRef);
        entity.setSupplier(supplierRef);
        entity.setCreatedByUser(createdByRef);
        entity.setApprovedByUser(approvedByRef);
        entity.setInvoiceNumber(invoice.getInvoiceNumber());
        entity.setIssueDate(invoice.getIssueDate());
        entity.setDueDate(invoice.getDueDate());
        entity.setSubtotal(invoice.getSubTotal());
        entity.setTaxAmount(invoice.getTaxAmount());
        entity.setDiscountAmount(invoice.getDiscountAmount());
        entity.setTotalAmount(invoice.getTotalAmount());
        entity.setCurrency(invoice.getCurrency());
        entity.setCurrentStatus(invoice.getStatus());
        entity.setPaymentTerms(invoice.getPaymentTerms());
        entity.setNotes(invoice.getNotes());
        entity.setApprovedAt(invoice.getApprovedAt());
        entity.setDeletedAt(invoice.getDeletedAt());
        return entity;
    }
}
