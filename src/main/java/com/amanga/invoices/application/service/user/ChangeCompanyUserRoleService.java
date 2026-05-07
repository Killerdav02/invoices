package com.amanga.invoices.application.service.user;

import com.amanga.invoices.application.port.in.user.ChangeCompanyUserRoleUseCase;
import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.enums.CompanyUserRole;
import com.amanga.invoices.domain.exception.CompanyUserNotFoundException;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.CompanyUser;
import org.springframework.stereotype.Service;

@Service
public class ChangeCompanyUserRoleService implements ChangeCompanyUserRoleUseCase {

    private final CompanyUserRepositoryPort companyUserRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public ChangeCompanyUserRoleService(
            CompanyUserRepositoryPort companyUserRepository,
            CurrentUserProviderPort currentUserProvider) {
        this.companyUserRepository = companyUserRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public CompanyUser changeCompanyUserRole(Long companyUserId, Long companyId, CompanyUserRole newRole) {
        // 1. Obtener usuario actual
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validar que pertenece a companyId
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Validar que es ADMIN
        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only ADMIN users can change roles");
        }

        // 4. Buscar usuario objetivo
        CompanyUser targetUser = companyUserRepository.findById(companyUserId)
                .orElseThrow(() -> new CompanyUserNotFoundException(companyUserId));

        // 5. Validar que usuario objetivo pertenece a companyId
        if (!targetUser.getCompanyId().equals(companyId)) {
            throw new ForbiddenActionException(
                    "User " + companyUserId + " does not belong to company " + companyId);
        }

        // 6. Cambiar rol
        targetUser.setRole(newRole);

        // 7. Guardar
        return companyUserRepository.save(targetUser);
    }
}

