package com.amanga.invoices.infrastructure.adapter.in.web.dto.user;

import com.amanga.invoices.domain.enums.CompanyUserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterCompanyUserRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @NotNull(message = "Role is required")
    private CompanyUserRole role;

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public CompanyUserRole getRole() { return role; }
}
