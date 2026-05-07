package com.amanga.invoices.infrastructure.adapter.in.web.dto.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCompanyRequest {

    @NotBlank(message = "Company name is required")
    @Size(max = 255, message = "Company name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Tax ID is required")
    @Size(max = 50, message = "Tax ID must not exceed 50 characters")
    private String taxId;

    @NotBlank(message = "Country code is required")
    @Size(min = 2, max = 3, message = "Country code must be 2 or 3 characters")
    private String countryCode;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
    private String currency;

    public String getName() { return name; }
    public String getTaxId() { return taxId; }
    public String getCountryCode() { return countryCode; }
    public String getCurrency() { return currency; }
}
