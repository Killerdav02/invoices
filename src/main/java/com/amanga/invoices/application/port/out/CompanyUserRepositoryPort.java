package com.amanga.invoices.application.port.out;

import com.amanga.invoices.domain.model.CompanyUser;

import java.util.List;
import java.util.Optional;

public interface CompanyUserRepositoryPort {

    // Devuelve Optional — el Service decide si lanzar la excepción
    Optional<CompanyUser> findById(Long companyUserId);

    // Búsqueda por auth0UserId para autenticación
    Optional<CompanyUser> findByAuth0UserId(String auth0UserId);

    // Búsqueda por email dentro de una empresa (multitenant)
    Optional<CompanyUser> findByEmailAndCompanyId(String email, Long companyId);

    // Listar usuarios de una empresa — siempre filtrado por companyId
    List<CompanyUser> findAllByCompanyId(Long companyId);

    // Maneja tanto insert como update
    CompanyUser save(CompanyUser companyUser);

    // Soft delete — marca deleted_at, no borra el registro
    void softDelete(Long companyUserId);

    // Verificar si ya existe un usuario con ese email en la empresa
    boolean existsByEmailAndCompanyId(String email, Long companyId);

    // Verificar si existe algún usuario con ese email (sin filtrar por empresa)
    boolean existsByEmail(String email);
}
