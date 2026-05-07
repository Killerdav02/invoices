package com.amanga.invoices.infrastructure.adapter.in.web.dto.supplier;

import com.amanga.invoices.domain.enums.SupplierType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class SupplierResponse {

    private Long id;
    private Long companyId;
    private String name;
    private String taxId;
    private String email;
    private String phone;
    private String countryCode;
    private SupplierType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public SupplierResponse(Long id, Long companyId, String name, String taxId,
                            String email, String phone, String countryCode,
                            SupplierType type, LocalDateTime createdAt) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.taxId = taxId;
        this.email = email;
        this.phone = phone;
        this.countryCode = countryCode;
        this.type = type;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getCompanyId() { return companyId; }
    public String getName() { return name; }
    public String getTaxId() { return taxId; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCountryCode() { return countryCode; }
    public SupplierType getType() { return type; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
