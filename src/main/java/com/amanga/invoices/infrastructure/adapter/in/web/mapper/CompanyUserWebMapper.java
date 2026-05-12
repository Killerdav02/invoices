package com.amanga.invoices.infrastructure.adapter.in.web.mapper;

import com.amanga.invoices.application.port.in.user.InviteCompanyUserCommand;
import com.amanga.invoices.domain.model.CompanyUser;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.user.CompanyUserResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.user.CurrentUserResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.user.InviteCompanyUserRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.user.RegisterCompanyUserRequest;
import org.springframework.stereotype.Component;

@Component
public class CompanyUserWebMapper {

    public CompanyUser toDomain(RegisterCompanyUserRequest request, Long companyId) {
        return CompanyUser.builder()
                .companyId(companyId)
                .email(request.getEmail())
                .name(request.getName())
                .role(request.getRole())
                .build();
    }

    public CompanyUserResponse toResponse(CompanyUser user) {
        return new CompanyUserResponse(
                user.getId(),
                user.getCompanyId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt()
        );
    }

    public CurrentUserResponse toCurrentUserResponse(CompanyUser user) {
        return new CurrentUserResponse(
                user.getId(),
                user.getCompanyId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getStatus()
        );
    }

    public InviteCompanyUserCommand toInviteCommand(InviteCompanyUserRequest request) {
        return new InviteCompanyUserCommand(
                request.getEmail(),
                request.getName(),
                request.getRole()
        );
    }
}
