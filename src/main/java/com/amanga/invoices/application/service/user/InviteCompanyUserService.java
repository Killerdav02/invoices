package com.amanga.invoices.application.service.user;

import com.amanga.invoices.application.port.in.user.InviteCompanyUserCommand;
import com.amanga.invoices.application.port.in.user.InviteCompanyUserUseCase;
import com.amanga.invoices.application.port.out.Auth0ManagementPort;
import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.enums.CompanyUserRole;
import com.amanga.invoices.domain.enums.CompanyUserStatus;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.model.CompanyUser;
import org.springframework.stereotype.Service;

@Service
public class InviteCompanyUserService implements InviteCompanyUserUseCase {

    private final CompanyUserRepositoryPort companyUserRepository;
    private final CurrentUserProviderPort currentUserProvider;
    private final Auth0ManagementPort auth0Management;

    public InviteCompanyUserService(CompanyUserRepositoryPort companyUserRepository,
                                    CurrentUserProviderPort currentUserProvider,
                                    Auth0ManagementPort auth0Management) {
        this.companyUserRepository = companyUserRepository;
        this.currentUserProvider = currentUserProvider;
        this.auth0Management = auth0Management;
    }

    @Override
    public CompanyUser inviteUser(InviteCompanyUserCommand command) {
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only admins can invite company users");
        }

        if (companyUserRepository.existsByEmailAndCompanyId(command.getEmail(), currentUser.getCompanyId())) {
            throw new ForbiddenActionException(
                    "A user with email " + command.getEmail() + " already exists in this company");
        }

        String userName = command.getName();
        if (userName == null || userName.isBlank()) {
            userName = command.getEmail();
        }

        String auth0UserId = auth0Management.inviteUser(command.getEmail(), userName);

        try {
            return companyUserRepository.save(
                    CompanyUser.builder()
                            .companyId(currentUser.getCompanyId())
                            .auth0UserId(auth0UserId)
                            .email(command.getEmail())
                            .name(userName)
                            .role(command.getRole() == null ? CompanyUserRole.EMPLOYEE : command.getRole())
                            .status(CompanyUserStatus.INVITED)
                            .build()
            );
        } catch (Exception ex) {
            auth0Management.deleteUser(auth0UserId);
            throw ex;
        }
    }
}