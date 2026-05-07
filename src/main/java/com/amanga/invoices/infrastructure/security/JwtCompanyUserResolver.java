package com.amanga.invoices.infrastructure.security;

import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.domain.exception.CompanyUserNotFoundException;
import com.amanga.invoices.domain.model.CompanyUser;
import org.springframework.stereotype.Component;

@Component
public class JwtCompanyUserResolver {

    private final CompanyUserRepositoryPort companyUserRepository;

    public JwtCompanyUserResolver(CompanyUserRepositoryPort companyUserRepository) {
        this.companyUserRepository = companyUserRepository;
    }

    public CompanyUser resolve(String auth0UserId) {
        return companyUserRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new CompanyUserNotFoundException(auth0UserId));
    }
}
