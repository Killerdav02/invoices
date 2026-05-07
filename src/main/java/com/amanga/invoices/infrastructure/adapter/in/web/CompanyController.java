package com.amanga.invoices.infrastructure.adapter.in.web;

import com.amanga.invoices.application.port.in.company.CreateCompanyUseCase;
import com.amanga.invoices.application.port.in.company.GetCompanyUseCase;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.CompanyResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.CreateCompanyRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.mapper.CompanyWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CreateCompanyUseCase createCompanyUseCase;
    private final GetCompanyUseCase getCompanyUseCase;
    private final CompanyWebMapper companyWebMapper;

    public CompanyController(CreateCompanyUseCase createCompanyUseCase,
                             GetCompanyUseCase getCompanyUseCase,
                             CompanyWebMapper companyWebMapper) {
        this.createCompanyUseCase = createCompanyUseCase;
        this.getCompanyUseCase = getCompanyUseCase;
        this.companyWebMapper = companyWebMapper;
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CreateCompanyRequest request) {

        CompanyResponse response = companyWebMapper.toResponse(
                createCompanyUseCase.createCompany(companyWebMapper.toDomain(request))
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompany(@PathVariable Long companyId) {

        CompanyResponse response = companyWebMapper.toResponse(
                getCompanyUseCase.getCompany(companyId)
        );

        return ResponseEntity.ok(response);
    }
}
