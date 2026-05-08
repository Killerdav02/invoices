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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // 400 — Validation errors (@Valid)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ApiErrorResponse handleValidation(MethodArgumentNotValidException ex) {
                String message = ex.getBindingResult().getFieldErrors().stream()
                                .map(FieldError::getDefaultMessage)
                                .collect(Collectors.joining(", "));
                return new ApiErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "VALIDATION_ERROR",
                                message);
        }

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
        public ResponseEntity<ApiErrorResponse> handleInvalidStatus(RuntimeException ex) {
                return ResponseEntity.status(422)
                                .body(new ApiErrorResponse(
                                                422,
                                                "UNPROCESSABLE_ENTITY",
                                                ex.getMessage()));
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
