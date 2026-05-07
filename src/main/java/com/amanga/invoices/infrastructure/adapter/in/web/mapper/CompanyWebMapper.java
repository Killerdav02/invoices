package com.amanga.invoices.infrastructure.adapter.in.web.mapper;

import com.amanga.invoices.domain.model.Company;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.CompanyResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.CreateCompanyRequest;
import org.springframework.stereotype.Component;

@Component
public class CompanyWebMapper {

    public Company toDomain(CreateCompanyRequest request) {
        return Company.builder()
                .name(request.getName())
                .taxId(request.getTaxId())
                .countryCode(request.getCountryCode())
                .currency(request.getCurrency())
                .build();
    }

    public CompanyResponse toResponse(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getTaxId(),
                company.getCountryCode(),
                company.getCurrency(),
                company.getCreatedAt()
        );
    }
}
