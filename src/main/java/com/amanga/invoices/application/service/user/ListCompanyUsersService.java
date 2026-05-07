package com.amanga.invoices.application.service.user;

import com.amanga.invoices.application.port.in.user.ListCompanyUsersUseCase;
import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.CompanyUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCompanyUsersService implements ListCompanyUsersUseCase {

    private final CompanyUserRepositoryPort companyUserRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public ListCompanyUsersService(CompanyUserRepositoryPort companyUserRepository,
                                   CurrentUserProviderPort currentUserProvider) {
        this.companyUserRepository = companyUserRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public List<CompanyUser> listCompanyUsers(Long companyId) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Return all users for the company
        return companyUserRepository.findAllByCompanyId(companyId);
    }
}
