package com.amanga.invoices.infrastructure.adapter.in.web;

import com.amanga.invoices.application.port.in.invoice.ApproveInvoiceUseCase;
import com.amanga.invoices.application.port.in.invoice.CancelInvoiceUseCase;
import com.amanga.invoices.application.port.in.invoice.CreateInvoiceUseCase;
import com.amanga.invoices.application.port.in.invoice.GetInvoiceUseCase;
import com.amanga.invoices.application.port.in.invoice.ListInvoicesUseCase;
import com.amanga.invoices.application.port.in.invoice.MarkInvoiceAsPaidUseCase;
import com.amanga.invoices.application.port.in.invoice.RegisterInvoiceSourceUseCase;
import com.amanga.invoices.application.port.in.invoice.RejectInvoiceUseCase;
import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.CreateInvoiceRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.InvoiceResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.RegisterInvoiceSourceRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.RejectInvoiceRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.mapper.InvoiceWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/companies/{companyId}/invoices")
public class InvoiceController {

    private final CreateInvoiceUseCase createInvoiceUseCase;
    private final GetInvoiceUseCase getInvoiceUseCase;
    private final ListInvoicesUseCase listInvoicesUseCase;
    private final ApproveInvoiceUseCase approveInvoiceUseCase;
    private final RejectInvoiceUseCase rejectInvoiceUseCase;
    private final CancelInvoiceUseCase cancelInvoiceUseCase;
    private final MarkInvoiceAsPaidUseCase markInvoiceAsPaidUseCase;
    private final RegisterInvoiceSourceUseCase registerInvoiceSourceUseCase;
    private final InvoiceWebMapper invoiceWebMapper;

    public InvoiceController(CreateInvoiceUseCase createInvoiceUseCase,
                             GetInvoiceUseCase getInvoiceUseCase,
                             ListInvoicesUseCase listInvoicesUseCase,
                             ApproveInvoiceUseCase approveInvoiceUseCase,
                             RejectInvoiceUseCase rejectInvoiceUseCase,
                             CancelInvoiceUseCase cancelInvoiceUseCase,
                             MarkInvoiceAsPaidUseCase markInvoiceAsPaidUseCase,
                             RegisterInvoiceSourceUseCase registerInvoiceSourceUseCase,
                             InvoiceWebMapper invoiceWebMapper) {
        this.createInvoiceUseCase = createInvoiceUseCase;
        this.getInvoiceUseCase = getInvoiceUseCase;
        this.listInvoicesUseCase = listInvoicesUseCase;
        this.approveInvoiceUseCase = approveInvoiceUseCase;
        this.rejectInvoiceUseCase = rejectInvoiceUseCase;
        this.cancelInvoiceUseCase = cancelInvoiceUseCase;
        this.markInvoiceAsPaidUseCase = markInvoiceAsPaidUseCase;
        this.registerInvoiceSourceUseCase = registerInvoiceSourceUseCase;
        this.invoiceWebMapper = invoiceWebMapper;
    }

    @PostMapping
    public ResponseEntity<InvoiceResponse> createInvoice(
            @PathVariable Long companyId,
            @Valid @RequestBody CreateInvoiceRequest request) {

        InvoiceResponse response = invoiceWebMapper.toResponse(
                createInvoiceUseCase.createInvoice(
                        invoiceWebMapper.toDomain(request, companyId),
                        invoiceWebMapper.toLineItemDomain(request.getLineItems()),
                        invoiceWebMapper.toSourceDomain(request.getSource())
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> listInvoices(
            @PathVariable Long companyId,
            @RequestParam(required = false) InvoiceStatus status,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        List<InvoiceResponse> response;

        if (status != null) {
            response = listInvoicesUseCase.listByStatus(companyId, status, page, size)
                    .stream().map(invoiceWebMapper::toResponse).collect(Collectors.toList());
        } else if (supplierId != null) {
            response = listInvoicesUseCase.listBySupplier(companyId, supplierId, page, size)
                    .stream().map(invoiceWebMapper::toResponse).collect(Collectors.toList());
        } else {
            response = listInvoicesUseCase.listByCompany(companyId, page, size)
                    .stream().map(invoiceWebMapper::toResponse).collect(Collectors.toList());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponse> getInvoice(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId) {

        return ResponseEntity.ok(
                invoiceWebMapper.toResponse(getInvoiceUseCase.getInvoice(invoiceId, companyId))
        );
    }

    @PostMapping("/{invoiceId}/approve")
    public ResponseEntity<InvoiceResponse> approveInvoice(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId) {

        return ResponseEntity.ok(
                invoiceWebMapper.toResponse(approveInvoiceUseCase.approveInvoice(invoiceId, companyId))
        );
    }

    @PostMapping("/{invoiceId}/reject")
    public ResponseEntity<Void> rejectInvoice(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId,
            @Valid @RequestBody RejectInvoiceRequest request) {

        rejectInvoiceUseCase.rejectInvoice(invoiceId, companyId, request.getReason());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{invoiceId}/cancel")
    public ResponseEntity<InvoiceResponse> cancelInvoice(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId) {

        return ResponseEntity.ok(
                invoiceWebMapper.toResponse(cancelInvoiceUseCase.cancelInvoice(invoiceId, companyId))
        );
    }

    @PostMapping("/{invoiceId}/pay")
    public ResponseEntity<InvoiceResponse> markInvoiceAsPaid(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId) {

        return ResponseEntity.ok(
                invoiceWebMapper.toResponse(markInvoiceAsPaidUseCase.markInvoiceAsPaid(invoiceId, companyId))
        );
    }

    @PostMapping("/{invoiceId}/sources")
    public ResponseEntity<Void> registerInvoiceSource(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId,
            @Valid @RequestBody RegisterInvoiceSourceRequest request) {

        registerInvoiceSourceUseCase.registerInvoiceSource(
                invoiceWebMapper.toSourceDomain(request, invoiceId)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
