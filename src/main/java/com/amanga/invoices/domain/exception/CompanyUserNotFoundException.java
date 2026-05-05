package com.amanga.invoices.domain.exception;

public class CompanyUserNotFoundException extends RuntimeException {

	public CompanyUserNotFoundException(Long companyUserId) {
		super("Company user not found with id: " + companyUserId);
	}
}
