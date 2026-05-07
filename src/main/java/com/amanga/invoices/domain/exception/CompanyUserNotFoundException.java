package com.amanga.invoices.domain.exception;

public class CompanyUserNotFoundException extends RuntimeException {

	public CompanyUserNotFoundException(Long companyUserId) {
		super("Company user not found with id: " + companyUserId);
	}

	public CompanyUserNotFoundException(String auth0UserId) {
		super("Company user not found with auth0UserId: " + auth0UserId);
	}
}
