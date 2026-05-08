package com.amanga.invoices.application.port.in.user;

import com.amanga.invoices.domain.model.CompanyUser;

public interface RegisterCompanyUserUseCase {

    CompanyUser registerCompanyUser(CompanyUser companyUser, String password);
}
