package com.amanga.invoices.application.service.company;

import com.amanga.invoices.application.port.in.company.CreateCompanyUseCase;
import com.amanga.invoices.application.port.out.CompanyRepositoryPort;
import com.amanga.invoices.domain.model.Company;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyService implements CreateCompanyUseCase {

    private final CompanyRepositoryPort companyRepository;

    public CreateCompanyService(CompanyRepositoryPort companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }
}
