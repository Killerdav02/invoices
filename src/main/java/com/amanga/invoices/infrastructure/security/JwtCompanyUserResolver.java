package com.amanga.invoices.infrastructure.security;

import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.domain.enums.CompanyUserStatus;
import com.amanga.invoices.domain.exception.CompanyUserNotFoundException;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.model.CompanyUser;
import org.springframework.stereotype.Component;

@Component
public class JwtCompanyUserResolver {

    private final CompanyUserRepositoryPort companyUserRepository;

    public JwtCompanyUserResolver(CompanyUserRepositoryPort companyUserRepository) {
        this.companyUserRepository = companyUserRepository;
    }

    public CompanyUser resolve(String auth0UserId) {
        if (auth0UserId != null && auth0UserId.endsWith("@clients")) {
            throw new ForbiddenActionException(
                    "Machine-to-machine tokens are not allowed for user-scoped endpoints");
        }

        CompanyUser companyUser = companyUserRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new CompanyUserNotFoundException(auth0UserId));

        if (companyUser.getStatus() == CompanyUserStatus.INVITED) {
            companyUser.setStatus(CompanyUserStatus.ACTIVE);
            return companyUserRepository.save(companyUser);
        }

        return companyUser;
    }
}
