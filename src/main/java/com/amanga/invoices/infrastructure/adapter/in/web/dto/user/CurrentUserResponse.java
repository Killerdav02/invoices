package com.amanga.invoices.infrastructure.adapter.in.web.dto.user;

import com.amanga.invoices.domain.enums.CompanyUserRole;
import com.amanga.invoices.domain.enums.CompanyUserStatus;

public class CurrentUserResponse {

    private Long id;
    private Long companyId;
    private String email;
    private String name;
    private CompanyUserRole role;
    private CompanyUserStatus status;

    public CurrentUserResponse(Long id, Long companyId, String email, String name,
                               CompanyUserRole role, CompanyUserStatus status) {
        this.id = id;
        this.companyId = companyId;
        this.email = email;
        this.name = name;
        this.role = role;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getCompanyId() { return companyId; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public CompanyUserRole getRole() { return role; }
    public CompanyUserStatus getStatus() { return status; }
}
