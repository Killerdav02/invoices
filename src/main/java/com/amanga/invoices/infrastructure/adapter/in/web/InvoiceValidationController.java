package com.amanga.invoices.infrastructure.adapter.in.web;

import com.amanga.invoices.application.port.in.invoice.ValidateInvoiceUseCase;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.InvoiceValidationResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.ValidateInvoiceRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.mapper.InvoiceWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/companies/{companyId}/invoices/{invoiceId}/validations")
public class InvoiceValidationController {

    private final ValidateInvoiceUseCase validateInvoiceUseCase;
    private final InvoiceWebMapper invoiceWebMapper;

    public InvoiceValidationController(ValidateInvoiceUseCase validateInvoiceUseCase,
                                       InvoiceWebMapper invoiceWebMapper) {
        this.validateInvoiceUseCase = validateInvoiceUseCase;
        this.invoiceWebMapper = invoiceWebMapper;
    }

    @PostMapping
    public ResponseEntity<InvoiceValidationResponse> validateInvoice(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId,
            @Valid @RequestBody ValidateInvoiceRequest request) {

        InvoiceValidationResponse response = invoiceWebMapper.toValidationResponse(
                validateInvoiceUseCase.validateInvoice(
                        invoiceId,
                        companyId,
                        request.getValidationRule(),
                        request.getStatus(),
                        request.getNotes()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
