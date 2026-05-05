package com.amanga.invoices.application.port.out;

import com.amanga.invoices.domain.model.Company;

import java.util.Optional;

public interface CompanyRepositoryPort {

    // Devuelve Optional — el Service decide si lanzar la excepción
    Optional<Company> findById(Long companyId);

    // Maneja tanto insert como update
    Company save(Company company);

    // Soft delete — marca deleted_at, no borra el registro
    void softDelete(Long companyId);

    // Verificar existencia sin cargar el objeto completo
    boolean existsById(Long companyId);
}
