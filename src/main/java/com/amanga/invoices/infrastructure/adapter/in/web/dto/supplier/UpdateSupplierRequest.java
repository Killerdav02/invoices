package com.amanga.invoices.infrastructure.adapter.in.web.dto.supplier;

import com.amanga.invoices.domain.enums.SupplierType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateSupplierRequest {

    @NotBlank(message = "Supplier name is required")
    @Size(max = 255, message = "Supplier name must not exceed 255 characters")
    private String name;

    @Email(message = "Email must be a valid address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @Size(max = 30, message = "Phone must not exceed 30 characters")
    private String phone;

    @NotBlank(message = "Country code is required")
    @Size(min = 2, max = 3, message = "Country code must be 2 or 3 characters")
    private String countryCode;

    @NotNull(message = "Supplier type is required")
    private SupplierType type;

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCountryCode() { return countryCode; }
    public SupplierType getType() { return type; }
}
