package com.amanga.invoices.infrastructure.adapter.in.web;

import com.amanga.invoices.application.port.in.supplier.GetSupplierUseCase;
import com.amanga.invoices.application.port.in.supplier.ListSuppliersUseCase;
import com.amanga.invoices.application.port.in.supplier.RegisterSupplierUseCase;
import com.amanga.invoices.application.port.in.supplier.UpdateSupplierUseCase;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.supplier.CreateSupplierRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.supplier.SupplierResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.supplier.UpdateSupplierRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.mapper.SupplierWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/companies/{companyId}/suppliers")
public class SupplierController {

    private final RegisterSupplierUseCase registerSupplierUseCase;
    private final GetSupplierUseCase getSupplierUseCase;
    private final ListSuppliersUseCase listSuppliersUseCase;
    private final UpdateSupplierUseCase updateSupplierUseCase;
    private final SupplierWebMapper supplierWebMapper;

    public SupplierController(RegisterSupplierUseCase registerSupplierUseCase,
                              GetSupplierUseCase getSupplierUseCase,
                              ListSuppliersUseCase listSuppliersUseCase,
                              UpdateSupplierUseCase updateSupplierUseCase,
                              SupplierWebMapper supplierWebMapper) {
        this.registerSupplierUseCase = registerSupplierUseCase;
        this.getSupplierUseCase = getSupplierUseCase;
        this.listSuppliersUseCase = listSuppliersUseCase;
        this.updateSupplierUseCase = updateSupplierUseCase;
        this.supplierWebMapper = supplierWebMapper;
    }

    @PostMapping
    public ResponseEntity<SupplierResponse> registerSupplier(
            @PathVariable Long companyId,
            @Valid @RequestBody CreateSupplierRequest request) {

        SupplierResponse response = supplierWebMapper.toResponse(
                registerSupplierUseCase.registerSupplier(
                        supplierWebMapper.toDomain(request, companyId)
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> listSuppliers(@PathVariable Long companyId) {

        List<SupplierResponse> response = listSuppliersUseCase.listSuppliers(companyId)
                .stream()
                .map(supplierWebMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<SupplierResponse> getSupplier(
            @PathVariable Long companyId,
            @PathVariable Long supplierId) {

        SupplierResponse response = supplierWebMapper.toResponse(
                getSupplierUseCase.getSupplier(supplierId, companyId)
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<SupplierResponse> updateSupplier(
            @PathVariable Long companyId,
            @PathVariable Long supplierId,
            @Valid @RequestBody UpdateSupplierRequest request) {

        SupplierResponse response = supplierWebMapper.toResponse(
                updateSupplierUseCase.updateSupplier(
                        supplierWebMapper.toDomain(request, supplierId, companyId)
                )
        );

        return ResponseEntity.ok(response);
    }
}
