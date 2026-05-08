package com.amanga.invoices.infrastructure.adapter.out.auth0;

import com.amanga.invoices.application.port.out.Auth0ManagementPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Map;

@Component
public class Auth0ManagementAdapter implements Auth0ManagementPort {

    private final RestClient restClient;
    private final String domain;
    private final String clientId;
    private final String clientSecret;

    // Simple in-memory token cache
    private String cachedToken;
    private Instant tokenExpiry = Instant.EPOCH;

    public Auth0ManagementAdapter(
            @Value("${auth0.management.domain}") String domain,
            @Value("${auth0.management.client-id}") String clientId,
            @Value("${auth0.management.client-secret}") String clientSecret) {
        this.domain = domain;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restClient = RestClient.builder()
                .baseUrl("https://" + domain)
                .build();
    }

    @Override
    public String createUser(String email, String password, String name) {
        String token = getManagementToken();

        Map<?, ?> response = restClient.post()
            .uri("/api/v2/users")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Map.of(
                "email", email,
                "password", password,
                "name", name,
                "connection", "Username-Password-Authentication"
            ))
            .retrieve()
            .body(Map.class);

        if (response == null || !response.containsKey("user_id")) {
            throw new IllegalStateException("Auth0 did not return a user_id for email: " + email);
        }
        return (String) response.get("user_id");
    }

    @Override
    public void deleteUser(String auth0UserId) {
        String token = getManagementToken();
        // auth0UserId contains '|' — encode it for the URL
        String encodedId = auth0UserId.replace("|", "%7C");

        restClient.delete()
                .uri("/api/v2/users/" + encodedId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity();
    }

    private String getManagementToken() {
        if (cachedToken != null && Instant.now().isBefore(tokenExpiry)) {
            return cachedToken;
        }

        Map<?, ?> response = restClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "audience", "https://" + domain + "/api/v2/",
                        "grant_type", "client_credentials"
                ))
                .retrieve()
                .body(Map.class);

        if (response == null || !response.containsKey("access_token")) {
            throw new IllegalStateException("Failed to obtain Auth0 Management API token");
        }

        cachedToken = (String) response.get("access_token");
        // Auth0 management tokens are valid for 86400s (24h) — cache for 23h
        tokenExpiry = Instant.now().plusSeconds(82800);
        return cachedToken;
    }
}
