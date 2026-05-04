package com.amanga.invoices.domain.model;

import java.time.LocalDateTime;

import com.amanga.invoices.domain.enums.SupplierType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Supplier {

    private Long id;
    private Long companyId;
    private Long createdByUserId;
    private String name;
    private String taxId;
    private String email;
    private String phone;
    private String countryCode;
    private SupplierType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}