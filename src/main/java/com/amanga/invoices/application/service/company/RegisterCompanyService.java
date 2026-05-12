package com.amanga.invoices.application.service.company;

import com.amanga.invoices.application.port.in.company.RegisterCompanyCommand;
import com.amanga.invoices.application.port.in.company.RegisterCompanyResult;
import com.amanga.invoices.application.port.in.company.RegisterCompanyUseCase;
import com.amanga.invoices.application.port.out.CompanyRepositoryPort;
import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.domain.enums.CompanyUserRole;
import com.amanga.invoices.domain.enums.CompanyUserStatus;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.model.Company;
import com.amanga.invoices.domain.model.CompanyUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterCompanyService implements RegisterCompanyUseCase {

    private final CompanyRepositoryPort companyRepository;
    private final CompanyUserRepositoryPort companyUserRepository;

    public RegisterCompanyService(CompanyRepositoryPort companyRepository,
                                  CompanyUserRepositoryPort companyUserRepository) {
        this.companyRepository = companyRepository;
        this.companyUserRepository = companyUserRepository;
    }

    @Override
    @Transactional
    public RegisterCompanyResult registerCompany(RegisterCompanyCommand command) {
        if (companyUserRepository.findByAuth0UserId(command.getAuth0UserId()).isPresent()) {
            throw new ForbiddenActionException("This Auth0 account is already linked to a company user");
        }

        Company createdCompany = companyRepository.save(
                Company.builder()
                        .name(command.getCompanyName())
                        .taxId(command.getTaxId())
                        .countryCode(command.getCountryCode())
                        .currency(command.getCurrency())
                        .build()
        );

        CompanyUser adminUser = companyUserRepository.save(
                CompanyUser.builder()
                        .companyId(createdCompany.getId())
                        .auth0UserId(command.getAuth0UserId())
                        .email(command.getAdminEmail())
                        .name(command.getAdminName())
                        .role(CompanyUserRole.ADMIN)
                        .status(CompanyUserStatus.ACTIVE)
                        .build()
        );

        return new RegisterCompanyResult(createdCompany, adminUser);
    }
}