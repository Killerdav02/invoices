package com.amanga.invoices.infrastructure.adapter.in.web.dto.company;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CompanyResponse {

    private Long id;
    private String name;
    private String taxId;
    private String countryCode;
    private String currency;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public CompanyResponse(Long id, String name, String taxId,
                           String countryCode, String currency,
                           LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.taxId = taxId;
        this.countryCode = countryCode;
        this.currency = currency;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getTaxId() { return taxId; }
    public String getCountryCode() { return countryCode; }
    public String getCurrency() { return currency; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
