package com.amanga.invoices.infrastructure.adapter.in.web.mapper;

import com.amanga.invoices.domain.model.Supplier;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.supplier.CreateSupplierRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.supplier.SupplierResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.supplier.UpdateSupplierRequest;
import org.springframework.stereotype.Component;

@Component
public class SupplierWebMapper {

    public Supplier toDomain(CreateSupplierRequest request, Long companyId) {
        return Supplier.builder()
                .companyId(companyId)
                .name(request.getName())
                .taxId(request.getTaxId())
                .email(request.getEmail())
                .phone(request.getPhone())
                .countryCode(request.getCountryCode())
                .type(request.getType())
                .build();
    }

    public Supplier toDomain(UpdateSupplierRequest request, Long supplierId, Long companyId) {
        return Supplier.builder()
                .id(supplierId)
                .companyId(companyId)
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .countryCode(request.getCountryCode())
                .type(request.getType())
                .build();
    }

    public SupplierResponse toResponse(Supplier supplier) {
        return new SupplierResponse(
                supplier.getId(),
                supplier.getCompanyId(),
                supplier.getName(),
                supplier.getTaxId(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.getCountryCode(),
                supplier.getType(),
                supplier.getCreatedAt()
        );
    }
}
