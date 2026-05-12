package com.amanga.invoices.infrastructure.adapter.in.web;

import com.amanga.invoices.application.port.in.user.InviteCompanyUserUseCase;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.user.CompanyUserResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.user.InviteCompanyUserRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.mapper.CompanyUserWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserInvitationController {

    private final InviteCompanyUserUseCase inviteCompanyUserUseCase;
    private final CompanyUserWebMapper companyUserWebMapper;

    public UserInvitationController(InviteCompanyUserUseCase inviteCompanyUserUseCase,
                                    CompanyUserWebMapper companyUserWebMapper) {
        this.inviteCompanyUserUseCase = inviteCompanyUserUseCase;
        this.companyUserWebMapper = companyUserWebMapper;
    }

    @PostMapping("/invite")
    public ResponseEntity<CompanyUserResponse> inviteCompanyUser(
            @Valid @RequestBody InviteCompanyUserRequest request) {

        CompanyUserResponse response = companyUserWebMapper.toResponse(
                inviteCompanyUserUseCase.inviteUser(companyUserWebMapper.toInviteCommand(request))
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
