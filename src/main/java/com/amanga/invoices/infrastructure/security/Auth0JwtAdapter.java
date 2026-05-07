package com.amanga.invoices.infrastructure.security;

import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.model.CompanyUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class Auth0JwtAdapter implements CurrentUserProviderPort {

    private final JwtCompanyUserResolver jwtCompanyUserResolver;

    public Auth0JwtAdapter(JwtCompanyUserResolver jwtCompanyUserResolver) {
        this.jwtCompanyUserResolver = jwtCompanyUserResolver;
    }

    @Override
    public CurrentUser getCurrentUser() {
        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = authentication.getToken();
        String auth0UserId = jwt.getSubject();

        CompanyUser companyUser = jwtCompanyUserResolver.resolve(auth0UserId);

        return new CurrentUser(
                companyUser.getId(),
                companyUser.getCompanyId(),
                companyUser.getEmail(),
                companyUser.getRole(),
                companyUser.getStatus()
        );
    }
}
