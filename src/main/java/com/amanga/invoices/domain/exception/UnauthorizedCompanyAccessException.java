package com.amanga.invoices.domain.exception;

public class UnauthorizedCompanyAccessException extends RuntimeException {

	public UnauthorizedCompanyAccessException(Long companyId) {
		super("Unauthorized access to company with id: " + companyId);
	}

	public UnauthorizedCompanyAccessException(Long userId, Long companyId) {
		super("User " + userId + " is not authorized to access company " + companyId);
	}
}
