package com.amanga.invoices.application.service.supplier;

import com.amanga.invoices.application.port.in.supplier.RegisterSupplierUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.SupplierRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Supplier;
import org.springframework.stereotype.Service;

@Service
public class RegisterSupplierService implements RegisterSupplierUseCase {

    private final SupplierRepositoryPort supplierRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public RegisterSupplierService(SupplierRepositoryPort supplierRepository,
                                   CurrentUserProviderPort currentUserProvider) {
        this.supplierRepository = supplierRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public Supplier registerSupplier(Supplier supplier) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(supplier.getCompanyId())) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), supplier.getCompanyId());
        }

        // 3. Validate current user is ADMIN
        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only admins can register suppliers");
        }

        // 4. Check for duplicate taxId within the same company
        if (supplierRepository.existsByTaxIdAndCompanyId(supplier.getTaxId(), supplier.getCompanyId())) {
            throw new ForbiddenActionException(
                    "A supplier with taxId " + supplier.getTaxId() + " already exists in this company");
        }

        // 5. Save and return
        return supplierRepository.save(supplier);
    }
}
