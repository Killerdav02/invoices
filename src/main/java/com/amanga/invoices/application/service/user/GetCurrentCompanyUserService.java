package com.amanga.invoices.application.service.user;

import com.amanga.invoices.application.port.in.user.GetCurrentCompanyUserUseCase;
import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.domain.exception.CompanyUserNotFoundException;
import com.amanga.invoices.domain.model.CompanyUser;
import org.springframework.stereotype.Service;

@Service
public class GetCurrentCompanyUserService implements GetCurrentCompanyUserUseCase {

    private final CompanyUserRepositoryPort companyUserRepository;

    public GetCurrentCompanyUserService(CompanyUserRepositoryPort companyUserRepository) {
        this.companyUserRepository = companyUserRepository;
    }

    @Override
    public CompanyUser getCurrentCompanyUser(String auth0UserId) {
        return companyUserRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new CompanyUserNotFoundException(auth0UserId));
    }
}
