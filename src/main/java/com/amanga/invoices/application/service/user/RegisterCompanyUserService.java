package com.amanga.invoices.application.service.user;

import com.amanga.invoices.application.port.in.user.RegisterCompanyUserUseCase;
import com.amanga.invoices.application.port.out.Auth0ManagementPort;
import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.CompanyUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterCompanyUserService implements RegisterCompanyUserUseCase {

    private static final Logger log = LoggerFactory.getLogger(RegisterCompanyUserService.class);

    private final CompanyUserRepositoryPort companyUserRepository;
    private final CurrentUserProviderPort currentUserProvider;
    private final Auth0ManagementPort auth0Management;

    public RegisterCompanyUserService(CompanyUserRepositoryPort companyUserRepository,
                                      CurrentUserProviderPort currentUserProvider,
                                      Auth0ManagementPort auth0Management) {
        this.companyUserRepository = companyUserRepository;
        this.currentUserProvider = currentUserProvider;
        this.auth0Management = auth0Management;
    }

    @Override
    public CompanyUser registerCompanyUser(CompanyUser companyUser, String password) {
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

        // Auth0 en la conexión DB no permite duplicados de email a nivel tenant.
        if (companyUserRepository.existsByEmail(companyUser.getEmail())) {
            throw new ForbiddenActionException(
                "A user with email " + companyUser.getEmail() + " already exists in another company");
        }

        // 5. Create user in Auth0 — get back the auth0_user_id
        String auth0UserId = auth0Management.createUser(
                companyUser.getEmail(), password, companyUser.getName());

        // 6. Save to DB — if it fails, delete the Auth0 user (compensating action)
        companyUser.setAuth0UserId(auth0UserId);
        try {
            return companyUserRepository.save(companyUser);
        } catch (RuntimeException dbException) {
            try {
                auth0Management.deleteUser(auth0UserId);
            } catch (RuntimeException rollbackException) {
                log.error("Failed to rollback Auth0 user {} after DB failure", auth0UserId, rollbackException);
                dbException.addSuppressed(rollbackException);
            }
            throw dbException;
        }
    }
}
