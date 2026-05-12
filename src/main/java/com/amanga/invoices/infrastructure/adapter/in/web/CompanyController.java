package com.amanga.invoices.infrastructure.adapter.in.web;

import com.amanga.invoices.application.port.in.company.CreateCompanyUseCase;
import com.amanga.invoices.application.port.in.company.GetCompanyUseCase;
import com.amanga.invoices.application.port.in.company.RegisterCompanyUseCase;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.CompanyResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.CreateCompanyRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.RegisterCompanyRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.company.RegisterCompanyResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.mapper.CompanyWebMapper;
import com.amanga.invoices.infrastructure.security.JwtCompanyUserResolver;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    private final RegisterCompanyUseCase registerCompanyUseCase;
    private final CompanyWebMapper companyWebMapper;
    private final JwtCompanyUserResolver jwtCompanyUserResolver;

    public CompanyController(CreateCompanyUseCase createCompanyUseCase,
                             GetCompanyUseCase getCompanyUseCase,
                             RegisterCompanyUseCase registerCompanyUseCase,
                             CompanyWebMapper companyWebMapper,
                             JwtCompanyUserResolver jwtCompanyUserResolver) {
        this.createCompanyUseCase = createCompanyUseCase;
        this.getCompanyUseCase = getCompanyUseCase;
        this.registerCompanyUseCase = registerCompanyUseCase;
        this.companyWebMapper = companyWebMapper;
        this.jwtCompanyUserResolver = jwtCompanyUserResolver;
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
    public ResponseEntity<CompanyResponse> getCompany(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long companyId) {

        Long userCompanyId = jwtCompanyUserResolver.resolve(jwt.getSubject()).getCompanyId();
        if (!userCompanyId.equals(companyId)) {
            throw new ForbiddenActionException("Access denied to company " + companyId);
        }

        CompanyResponse response = companyWebMapper.toResponse(
                getCompanyUseCase.getCompany(companyId)
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterCompanyResponse> registerCompany(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody RegisterCompanyRequest request) {

        String adminEmail = jwt.getClaimAsString("email");
        if (adminEmail == null || adminEmail.isBlank()) {
            throw new ForbiddenActionException("Token does not contain 'email' claim");
        }

        RegisterCompanyResponse response = companyWebMapper.toRegisterResponse(
                registerCompanyUseCase.registerCompany(
                        companyWebMapper.toRegisterCommand(request, jwt.getSubject(), adminEmail)
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
