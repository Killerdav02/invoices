package com.amanga.invoices.application.service.supplier;

import com.amanga.invoices.application.port.in.supplier.ListSuppliersUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.SupplierRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSuppliersService implements ListSuppliersUseCase {

    private final SupplierRepositoryPort supplierRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public ListSuppliersService(SupplierRepositoryPort supplierRepository,
                                CurrentUserProviderPort currentUserProvider) {
        this.supplierRepository = supplierRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public List<Supplier> listSuppliers(Long companyId) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Return all suppliers for the company
        return supplierRepository.findAllByCompanyId(companyId);
    }
}
