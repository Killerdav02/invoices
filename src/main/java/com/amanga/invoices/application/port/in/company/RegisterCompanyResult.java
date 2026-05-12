package com.amanga.invoices.application.port.in.company;

import com.amanga.invoices.domain.model.Company;
import com.amanga.invoices.domain.model.CompanyUser;

public class RegisterCompanyResult {

    private final Company company;
    private final CompanyUser adminUser;

    public RegisterCompanyResult(Company company, CompanyUser adminUser) {
        this.company = company;
        this.adminUser = adminUser;
    }

    public Company getCompany() { return company; }
    public CompanyUser getAdminUser() { return adminUser; }
}