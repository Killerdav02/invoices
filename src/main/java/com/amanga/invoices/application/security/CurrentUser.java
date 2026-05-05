package com.amanga.invoices.application.security;

import com.amanga.invoices.domain.enums.CompanyUserRole;
import com.amanga.invoices.domain.enums.CompanyUserStatus;

public class CurrentUser {

    private final Long id;
    private final Long companyId;
    private final String email;
    private final CompanyUserRole role;
    private final CompanyUserStatus status;

    public CurrentUser(
            Long id,
            Long companyId,
            String email,
            CompanyUserRole role,
            CompanyUserStatus status) {
        this.id = id;
        this.companyId = companyId;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    // Métodos de conveniencia para seguridad
    public boolean isAdmin() {
        return this.role == CompanyUserRole.ADMIN;
    }

    public boolean isActive() {
        return this.status == CompanyUserStatus.ACTIVE;
    }

    public boolean belongsToCompany(Long companyId) {
        return this.companyId.equals(companyId);
    }

    // Getters
    public Long getId() { return id; }
    public Long getCompanyId() { return companyId; }
    public String getEmail() { return email; }
    public CompanyUserRole getRole() { return role; }
    public CompanyUserStatus getStatus() { return status; }
}
