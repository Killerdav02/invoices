package com.amanga.invoices.application.port.in.user;

import com.amanga.invoices.domain.model.CompanyUser;

import java.util.List;

public interface ListCompanyUsersUseCase {

    List<CompanyUser> listCompanyUsers(Long companyId);
}
