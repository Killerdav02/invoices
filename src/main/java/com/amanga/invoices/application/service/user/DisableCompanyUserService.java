package com.amanga.invoices.application.service.user;

import com.amanga.invoices.application.port.in.user.DisableCompanyUserUseCase;
import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.enums.CompanyUserStatus;
import com.amanga.invoices.domain.exception.CompanyUserNotFoundException;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.CompanyUser;
import org.springframework.stereotype.Service;

@Service
public class DisableCompanyUserService implements DisableCompanyUserUseCase {

    private final CompanyUserRepositoryPort companyUserRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public DisableCompanyUserService(CompanyUserRepositoryPort companyUserRepository,
                                     CurrentUserProviderPort currentUserProvider) {
        this.companyUserRepository = companyUserRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public void disableCompanyUser(Long companyUserId, Long companyId) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Validate current user is ADMIN
        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only admins can disable company users");
        }

        // 4. Find target user
        CompanyUser companyUser = companyUserRepository.findById(companyUserId)
                .orElseThrow(() -> new CompanyUserNotFoundException(companyUserId));

        // 5. Validate target belongs to same company
        if (!companyUser.getCompanyId().equals(companyId)) {
            throw new ForbiddenActionException(
                    "User " + companyUserId + " does not belong to company " + companyId);
        }

        // 6. Validate not already disabled
        if (companyUser.getStatus() == CompanyUserStatus.DISABLED) {
            throw new ForbiddenActionException(
                    "User " + companyUserId + " is already disabled");
        }

        // 7. Set status and save
        companyUser.setStatus(CompanyUserStatus.DISABLED);
        companyUserRepository.save(companyUser);
    }
}
