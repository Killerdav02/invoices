package com.amanga.invoices.domain.exception;

public class UnauthorizedCompanyAccessException extends RuntimeException {

	public UnauthorizedCompanyAccessException(Long companyId) {
		super("Unauthorized access to company with id: " + companyId);
	}
}
