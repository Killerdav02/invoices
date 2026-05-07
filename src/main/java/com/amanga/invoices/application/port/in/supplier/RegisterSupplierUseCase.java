package com.amanga.invoices.application.port.in.supplier;

import com.amanga.invoices.domain.model.Supplier;

public interface RegisterSupplierUseCase {

    Supplier registerSupplier(Supplier supplier);
}
