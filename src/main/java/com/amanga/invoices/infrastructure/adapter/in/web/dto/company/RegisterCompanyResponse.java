package com.amanga.invoices.infrastructure.adapter.in.web.dto.company;

import com.amanga.invoices.domain.enums.CompanyUserRole;
import com.amanga.invoices.domain.enums.CompanyUserStatus;

import java.time.LocalDateTime;

public class RegisterCompanyResponse {

    private final Long companyId;
    private final String companyName;
    private final String taxId;
    private final String countryCode;
    private final String currency;
    private final Long adminUserId;
    private final String adminEmail;
    private final String adminName;
    private final CompanyUserRole adminRole;
    private final CompanyUserStatus adminStatus;
    private final LocalDateTime createdAt;

    public RegisterCompanyResponse(Long companyId,
                                   String companyName,
                                   String taxId,
                                   String countryCode,
                                   String currency,
                                   Long adminUserId,
                                   String adminEmail,
                                   String adminName,
                                   CompanyUserRole adminRole,
                                   CompanyUserStatus adminStatus,
                                   LocalDateTime createdAt) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.taxId = taxId;
        this.countryCode = countryCode;
        this.currency = currency;
        this.adminUserId = adminUserId;
        this.adminEmail = adminEmail;
        this.adminName = adminName;
        this.adminRole = adminRole;
        this.adminStatus = adminStatus;
        this.createdAt = createdAt;
    }

    public Long getCompanyId() { return companyId; }
    public String getCompanyName() { return companyName; }
    public String getTaxId() { return taxId; }
    public String getCountryCode() { return countryCode; }
    public String getCurrency() { return currency; }
    public Long getAdminUserId() { return adminUserId; }
    public String getAdminEmail() { return adminEmail; }
    public String getAdminName() { return adminName; }
    public CompanyUserRole getAdminRole() { return adminRole; }
    public CompanyUserStatus getAdminStatus() { return adminStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}