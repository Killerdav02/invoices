package com.amanga.invoices.infrastructure.adapter.in.web;

import com.amanga.invoices.application.port.in.user.ChangeCompanyUserRoleUseCase;
import com.amanga.invoices.application.port.in.user.DisableCompanyUserUseCase;
import com.amanga.invoices.application.port.in.user.ListCompanyUsersUseCase;
import com.amanga.invoices.application.port.in.user.RegisterCompanyUserUseCase;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.user.ChangeCompanyUserRoleRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.user.CompanyUserResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.user.RegisterCompanyUserRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.mapper.CompanyUserWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/companies/{companyId}/users")
public class CompanyUserController {

    private final RegisterCompanyUserUseCase registerCompanyUserUseCase;
    private final ListCompanyUsersUseCase listCompanyUsersUseCase;
    private final ChangeCompanyUserRoleUseCase changeCompanyUserRoleUseCase;
    private final DisableCompanyUserUseCase disableCompanyUserUseCase;
    private final CompanyUserWebMapper companyUserWebMapper;

    public CompanyUserController(RegisterCompanyUserUseCase registerCompanyUserUseCase,
                                 ListCompanyUsersUseCase listCompanyUsersUseCase,
                                 ChangeCompanyUserRoleUseCase changeCompanyUserRoleUseCase,
                                 DisableCompanyUserUseCase disableCompanyUserUseCase,
                                 CompanyUserWebMapper companyUserWebMapper) {
        this.registerCompanyUserUseCase = registerCompanyUserUseCase;
        this.listCompanyUsersUseCase = listCompanyUsersUseCase;
        this.changeCompanyUserRoleUseCase = changeCompanyUserRoleUseCase;
        this.disableCompanyUserUseCase = disableCompanyUserUseCase;
        this.companyUserWebMapper = companyUserWebMapper;
    }

    @PostMapping
    public ResponseEntity<CompanyUserResponse> registerCompanyUser(
            @PathVariable Long companyId,
            @Valid @RequestBody RegisterCompanyUserRequest request) {

        CompanyUserResponse response = companyUserWebMapper.toResponse(
                registerCompanyUserUseCase.registerCompanyUser(
                        companyUserWebMapper.toDomain(request, companyId),
                        request.getPassword()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CompanyUserResponse>> listCompanyUsers(
            @PathVariable Long companyId) {

        List<CompanyUserResponse> response = listCompanyUsersUseCase.listCompanyUsers(companyId)
                .stream()
                .map(companyUserWebMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{companyUserId}/role")
    public ResponseEntity<CompanyUserResponse> changeCompanyUserRole(
            @PathVariable Long companyId,
            @PathVariable Long companyUserId,
            @Valid @RequestBody ChangeCompanyUserRoleRequest request) {

        CompanyUserResponse response = companyUserWebMapper.toResponse(
                changeCompanyUserRoleUseCase.changeCompanyUserRole(
                        companyUserId, companyId, request.getRole()
                )
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{companyUserId}")
    public ResponseEntity<Void> disableCompanyUser(
            @PathVariable Long companyId,
            @PathVariable Long companyUserId) {

        disableCompanyUserUseCase.disableCompanyUser(companyUserId, companyId);

        return ResponseEntity.noContent().build();
    }
}
