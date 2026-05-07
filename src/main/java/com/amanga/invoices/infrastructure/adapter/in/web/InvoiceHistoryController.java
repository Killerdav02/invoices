package com.amanga.invoices.infrastructure.adapter.in.web;

import com.amanga.invoices.application.port.in.invoice.GetInvoiceHistoryUseCase;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.InvoiceStatusHistoryResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.mapper.InvoiceWebMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/companies/{companyId}/invoices/{invoiceId}/history")
public class InvoiceHistoryController {

    private final GetInvoiceHistoryUseCase getInvoiceHistoryUseCase;
    private final InvoiceWebMapper invoiceWebMapper;

    public InvoiceHistoryController(GetInvoiceHistoryUseCase getInvoiceHistoryUseCase,
                                    InvoiceWebMapper invoiceWebMapper) {
        this.getInvoiceHistoryUseCase = getInvoiceHistoryUseCase;
        this.invoiceWebMapper = invoiceWebMapper;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceStatusHistoryResponse>> getInvoiceHistory(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId) {

        List<InvoiceStatusHistoryResponse> response = getInvoiceHistoryUseCase
                .getInvoiceHistory(invoiceId, companyId)
                .stream()
                .map(invoiceWebMapper::toHistoryResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
