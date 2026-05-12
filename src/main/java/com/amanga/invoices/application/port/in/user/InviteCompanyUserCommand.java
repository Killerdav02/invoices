package com.amanga.invoices.application.port.in.user;

import com.amanga.invoices.domain.enums.CompanyUserRole;

public class InviteCompanyUserCommand {

    private final String email;
    private final String name;
    private final CompanyUserRole role;

    public InviteCompanyUserCommand(String email, String name, CompanyUserRole role) {
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }
    public CompanyUserRole getRole() { return role; }
}