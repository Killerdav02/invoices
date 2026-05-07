package com.amanga.invoices.application.port.in.user;

import com.amanga.invoices.domain.enums.CompanyUserRole;
import com.amanga.invoices.domain.model.CompanyUser;

public interface ChangeCompanyUserRoleUseCase {

    CompanyUser changeCompanyUserRole(Long companyUserId, Long companyId, CompanyUserRole newRole);
}
