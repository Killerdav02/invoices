package com.amanga.invoices.application.service.supplier;

import com.amanga.invoices.application.port.in.supplier.GetSupplierUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.SupplierRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.SupplierNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Supplier;
import org.springframework.stereotype.Service;

@Service
public class GetSupplierService implements GetSupplierUseCase {

    private final SupplierRepositoryPort supplierRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public GetSupplierService(SupplierRepositoryPort supplierRepository,
                              CurrentUserProviderPort currentUserProvider) {
        this.supplierRepository = supplierRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public Supplier getSupplier(Long supplierId, Long companyId) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Find supplier
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException(supplierId));

        // 4. Validate supplier belongs to the company
        if (!supplier.getCompanyId().equals(companyId)) {
            throw new ForbiddenActionException(
                    "Supplier " + supplierId + " does not belong to company " + companyId);
        }

        return supplier;
    }
}
