package com.amanga.invoices.infrastructure.security;

import java.security.Principal;

public class SecurityPrincipal implements Principal {

    private final String auth0UserId;

    public SecurityPrincipal(String auth0UserId) {
        this.auth0UserId = auth0UserId;
    }

    public String getAuth0UserId() {
        return auth0UserId;
    }

    @Override
    public String getName() {
        return auth0UserId;
    }
}
