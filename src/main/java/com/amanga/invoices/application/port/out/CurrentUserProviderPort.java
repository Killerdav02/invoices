package com.amanga.invoices.application.port.out;

import com.amanga.invoices.application.security.CurrentUser;

public interface CurrentUserProviderPort {
    CurrentUser getCurrentUser();
}
