package com.amanga.invoices.infrastructure.adapter.in.web.dto.user;

import com.amanga.invoices.domain.enums.CompanyUserRole;
import jakarta.validation.constraints.NotNull;

public class ChangeCompanyUserRoleRequest {

    @NotNull(message = "Role is required")
    private CompanyUserRole role;

    public CompanyUserRole getRole() { return role; }
}
