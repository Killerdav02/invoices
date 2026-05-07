package com.amanga.invoices.application.port.in.supplier;

import com.amanga.invoices.domain.model.Supplier;

public interface UpdateSupplierUseCase {

    Supplier updateSupplier(Supplier supplier);
}
