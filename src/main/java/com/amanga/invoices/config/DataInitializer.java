package com.amanga.invoices.config;

import com.amanga.invoices.application.port.out.Auth0ManagementPort;
import com.amanga.invoices.application.port.out.CompanyRepositoryPort;
import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.domain.enums.CompanyUserRole;
import com.amanga.invoices.domain.enums.CompanyUserStatus;
import com.amanga.invoices.domain.model.Company;
import com.amanga.invoices.domain.model.CompanyUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Component
@ConditionalOnProperty(name = "app.bootstrap.enabled", havingValue = "true")
public class DataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final CompanyRepositoryPort companyRepository;
    private final CompanyUserRepositoryPort companyUserRepository;
    private final Auth0ManagementPort auth0Management;

    @Value("${app.bootstrap.company.name}")
    private String companyName;

    @Value("${app.bootstrap.company.tax-id}")
    private String companyTaxId;

    @Value("${app.bootstrap.company.country-code}")
    private String companyCountryCode;

    @Value("${app.bootstrap.company.currency}")
    private String companyCurrency;

    @Value("${app.bootstrap.admin.email}")
    private String adminEmail;

    @Value("${app.bootstrap.admin.password}")
    private String adminPassword;

    @Value("${app.bootstrap.admin.name}")
    private String adminName;

    @Value("${app.bootstrap.admin.auth0-user-id:}")
    private String adminAuth0UserId;

    public DataInitializer(CompanyRepositoryPort companyRepository,
                           CompanyUserRepositoryPort companyUserRepository,
                           Auth0ManagementPort auth0Management) {
        this.companyRepository = companyRepository;
        this.companyUserRepository = companyUserRepository;
        this.auth0Management = auth0Management;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // Idempotente: si ya existe el admin, no hace nada
        if (companyUserRepository.existsByEmail(adminEmail)) {
            log.info("[Bootstrap] Admin user already exists, skipping initialization.");
            return;
        }

        log.info("[Bootstrap] No admin found — creating default company and admin user...");

        String auth0UserId = adminAuth0UserId;
        if (auth0UserId == null || auth0UserId.isBlank()) {
            try {
                auth0UserId = auth0Management.createUser(adminEmail, adminPassword, adminName);
                log.info("[Bootstrap] Auth0 user created: {}", auth0UserId);
            } catch (HttpClientErrorException.Conflict ex) {
                log.error("[Bootstrap] Auth0 reports user already exists for email '{}'. " +
                                "Set 'app.bootstrap.admin.auth0-user-id' with that user's id (sub) to complete bootstrap.",
                        adminEmail);
                return;
            }
        } else {
            log.info("[Bootstrap] Using configured Auth0 user id for admin bootstrap: {}", auth0UserId);
        }

        // 1. Crear empresa
        Company company = Company.builder()
                .name(companyName)
                .taxId(companyTaxId)
                .countryCode(companyCountryCode)
                .currency(companyCurrency)
                .build();
        Company savedCompany = companyRepository.save(company);
        log.info("[Bootstrap] Company created with id={}", savedCompany.getId());

        // 2. Guardar admin en BD
        CompanyUser admin = CompanyUser.builder()
                .companyId(savedCompany.getId())
                .auth0UserId(auth0UserId)
                .email(adminEmail)
                .name(adminName)
                .role(CompanyUserRole.ADMIN)
                .status(CompanyUserStatus.ACTIVE)
                .build();
        CompanyUser savedAdmin = companyUserRepository.save(admin);
        log.info("[Bootstrap] Admin user created with id={} — setup complete.", savedAdmin.getId());
    }
}
