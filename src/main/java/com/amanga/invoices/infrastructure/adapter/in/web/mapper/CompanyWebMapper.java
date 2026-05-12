package com.amanga.invoices.infrastructure.adapter.in.web.mapper;

import com.amanga.invoices.application.port.in.company.RegisterCompanyCommand;
import com.amanga.invoices.application.port.in.company.RegisterCompanyResult;
import com.amanga.invoices.domain.model.Company;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.CompanyResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.CreateCompanyRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.RegisterCompanyRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.RegisterCompanyResponse;
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

    public RegisterCompanyCommand toRegisterCommand(RegisterCompanyRequest request,
                                                    String auth0UserId,
                                                    String adminEmail) {
        String currency = request.getCurrency();
        if (currency == null || currency.isBlank()) {
            currency = "MXN";
        }

        return new RegisterCompanyCommand(
                auth0UserId,
                adminEmail,
                request.getCompanyName(),
                request.getTaxId(),
                request.getCountryCode(),
                currency,
                request.getAdminName()
        );
    }

    public RegisterCompanyResponse toRegisterResponse(RegisterCompanyResult result) {
        return new RegisterCompanyResponse(
                result.getCompany().getId(),
                result.getCompany().getName(),
                result.getCompany().getTaxId(),
                result.getCompany().getCountryCode(),
                result.getCompany().getCurrency(),
                result.getAdminUser().getId(),
                result.getAdminUser().getEmail(),
                result.getAdminUser().getName(),
                result.getAdminUser().getRole(),
                result.getAdminUser().getStatus(),
                result.getCompany().getCreatedAt()
        );
    }
}
