package com.amanga.invoices.application.port.in.supplier;

import com.amanga.invoices.domain.model.Supplier;

import java.util.List;

public interface ListSuppliersUseCase {

    List<Supplier> listSuppliers(Long companyId);
}
