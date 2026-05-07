package com.amanga.invoices.infrastructure.exception;

import com.amanga.invoices.domain.exception.CompanyNotFoundException;
import com.amanga.invoices.domain.exception.CompanyUserNotFoundException;
import com.amanga.invoices.domain.exception.DuplicateInvoiceException;
import com.amanga.invoices.domain.exception.DuplicateSupplierException;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.InvalidInvoiceStatusException;
import com.amanga.invoices.domain.exception.InvalidPaymentScheduleStatusException;
import com.amanga.invoices.domain.exception.InvoiceNotFoundException;
import com.amanga.invoices.domain.exception.PaymentScheduleNotFoundException;
import com.amanga.invoices.domain.exception.SupplierNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.common.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 — Not Found
    @ExceptionHandler({
            InvoiceNotFoundException.class,
            CompanyNotFoundException.class,
            CompanyUserNotFoundException.class,
            SupplierNotFoundException.class,
            PaymentScheduleNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(RuntimeException ex) {
        return new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                ex.getMessage());
    }

    // 403 — Forbidden
    @ExceptionHandler(ForbiddenActionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleForbidden(ForbiddenActionException ex) {
        return new ApiErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "FORBIDDEN",
                ex.getMessage());
    }

    // 401 — Unauthorized
    @ExceptionHandler(UnauthorizedCompanyAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleUnauthorized(UnauthorizedCompanyAccessException ex) {
        return new ApiErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "UNAUTHORIZED",
                ex.getMessage());
    }

    // 409 — Conflict
    @ExceptionHandler({
            DuplicateInvoiceException.class,
            DuplicateSupplierException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleConflict(RuntimeException ex) {
        return new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                ex.getMessage());
    }

    // 422 — Unprocessable Entity
    @ExceptionHandler({
            InvalidInvoiceStatusException.class,
            InvalidPaymentScheduleStatusException.class
    })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrorResponse handleInvalidStatus(RuntimeException ex) {
        return new ApiErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "UNPROCESSABLE_ENTITY",
                ex.getMessage());
    }

    // 500 — Internal Server Error (safety net)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleUnexpected(Exception ex) {
        return new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred");
    }
}
