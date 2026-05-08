package com.amanga.invoices.application.port.out;

public interface Auth0ManagementPort {

    /**
     * Creates a user in Auth0 and returns the auth0_user_id (sub).
     */
    String createUser(String email, String password, String name);

    /**
     * Deletes a user from Auth0 by their auth0_user_id.
     * Used as a compensating action if DB save fails after Auth0 creation.
     */
    void deleteUser(String auth0UserId);
}
