package com.amanga.invoices.application.port.in.user;

import com.amanga.invoices.domain.model.CompanyUser;

public interface GetCurrentCompanyUserUseCase {

    CompanyUser getCurrentCompanyUser(String auth0UserId);
}
