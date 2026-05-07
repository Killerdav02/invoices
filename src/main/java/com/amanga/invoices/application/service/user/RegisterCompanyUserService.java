package com.amanga.invoices.application.service.user;

import com.amanga.invoices.application.port.in.user.RegisterCompanyUserUseCase;
import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.CompanyUser;
import org.springframework.stereotype.Service;

@Service
public class RegisterCompanyUserService implements RegisterCompanyUserUseCase {

    private final CompanyUserRepositoryPort companyUserRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public RegisterCompanyUserService(CompanyUserRepositoryPort companyUserRepository,
                                      CurrentUserProviderPort currentUserProvider) {
        this.companyUserRepository = companyUserRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public CompanyUser registerCompanyUser(CompanyUser companyUser) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyUser.getCompanyId())) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyUser.getCompanyId());
        }

        // 3. Validate current user is ADMIN
        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only admins can register company users");
        }

        // 4. Check for duplicate email within the same company
        if (companyUserRepository.existsByEmailAndCompanyId(
                companyUser.getEmail(), companyUser.getCompanyId())) {
            throw new ForbiddenActionException(
                    "A user with email " + companyUser.getEmail() + " already exists in this company");
        }

        // 5. Save and return
        return companyUserRepository.save(companyUser);
    }
}
