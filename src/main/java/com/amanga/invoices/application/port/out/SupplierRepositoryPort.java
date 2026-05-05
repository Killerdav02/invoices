package com.amanga.invoices.application.port.out;

import com.amanga.invoices.domain.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierRepositoryPort {

    // Devuelve Optional — el Service decide si lanzar la excepción
    Optional<Supplier> findById(Long supplierId);

    // Búsqueda por taxId dentro de una empresa — base del control de duplicados
    Optional<Supplier> findByTaxIdAndCompanyId(String taxId, Long companyId);

    // Listar proveedores de una empresa — siempre filtrado por companyId
    List<Supplier> findAllByCompanyId(Long companyId);

    // Maneja tanto insert como update
    Supplier save(Supplier supplier);

    // Soft delete — marca deleted_at, no borra el registro
    void softDelete(Long supplierId);

    // Verificar si ya existe un proveedor con ese taxId en la empresa
    boolean existsByTaxIdAndCompanyId(String taxId, Long companyId);
}
