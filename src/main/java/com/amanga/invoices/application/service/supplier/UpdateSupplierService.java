package com.amanga.invoices.application.service.supplier;

import com.amanga.invoices.application.port.in.supplier.UpdateSupplierUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.SupplierRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.SupplierNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Supplier;
import org.springframework.stereotype.Service;

@Service
public class UpdateSupplierService implements UpdateSupplierUseCase {

    private final SupplierRepositoryPort supplierRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public UpdateSupplierService(SupplierRepositoryPort supplierRepository,
                                 CurrentUserProviderPort currentUserProvider) {
        this.supplierRepository = supplierRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(supplier.getCompanyId())) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), supplier.getCompanyId());
        }

        // 3. Validate current user is ADMIN
        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only admins can update suppliers");
        }

        // 4. Find existing supplier
        Supplier existing = supplierRepository.findById(supplier.getId())
                .orElseThrow(() -> new SupplierNotFoundException(supplier.getId()));

        // 5. Validate existing supplier belongs to the company
        if (!existing.getCompanyId().equals(supplier.getCompanyId())) {
            throw new ForbiddenActionException(
                    "Supplier " + supplier.getId() + " does not belong to company " + supplier.getCompanyId());
        }

        // 6. Update mutable fields
        existing.setName(supplier.getName());
        existing.setEmail(supplier.getEmail());
        existing.setPhone(supplier.getPhone());
        existing.setCountryCode(supplier.getCountryCode());
        existing.setType(supplier.getType());

        // 7. Save and return
        return supplierRepository.save(existing);
    }
}
