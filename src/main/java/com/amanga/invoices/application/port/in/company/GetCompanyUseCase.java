package com.amanga.invoices.application.port.in.company;

import com.amanga.invoices.domain.model.Company;

public interface GetCompanyUseCase {

    Company getCompany(Long companyId);
}
