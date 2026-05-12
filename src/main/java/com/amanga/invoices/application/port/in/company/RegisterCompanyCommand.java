package com.amanga.invoices.application.port.in.company;

public class RegisterCompanyCommand {

    private final String auth0UserId;
    private final String adminEmail;
    private final String companyName;
    private final String taxId;
    private final String countryCode;
    private final String currency;
    private final String adminName;

    public RegisterCompanyCommand(String auth0UserId,
                                  String adminEmail,
                                  String companyName,
                                  String taxId,
                                  String countryCode,
                                  String currency,
                                  String adminName) {
        this.auth0UserId = auth0UserId;
        this.adminEmail = adminEmail;
        this.companyName = companyName;
        this.taxId = taxId;
        this.countryCode = countryCode;
        this.currency = currency;
        this.adminName = adminName;
    }

    public String getAuth0UserId() { return auth0UserId; }
    public String getAdminEmail() { return adminEmail; }
    public String getCompanyName() { return companyName; }
    public String getTaxId() { return taxId; }
    public String getCountryCode() { return countryCode; }
    public String getCurrency() { return currency; }
    public String getAdminName() { return adminName; }
}