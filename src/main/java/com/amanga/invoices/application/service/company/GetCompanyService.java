package com.amanga.invoices.application.service.company;

import com.amanga.invoices.application.port.in.company.GetCompanyUseCase;
import com.amanga.invoices.application.port.out.CompanyRepositoryPort;
import com.amanga.invoices.domain.exception.CompanyNotFoundException;
import com.amanga.invoices.domain.model.Company;
import org.springframework.stereotype.Service;

@Service
public class GetCompanyService implements GetCompanyUseCase {

    private final CompanyRepositoryPort companyRepository;

    public GetCompanyService(CompanyRepositoryPort companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company getCompany(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));
    }
}
