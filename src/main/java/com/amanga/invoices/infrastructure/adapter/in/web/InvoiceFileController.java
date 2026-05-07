package com.amanga.invoices.infrastructure.adapter.in.web;

import com.amanga.invoices.application.port.in.invoice.UploadInvoiceFileUseCase;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.InvoiceFileResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.invoice.UploadInvoiceFileRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.mapper.InvoiceWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/companies/{companyId}/invoices/{invoiceId}/files")
public class InvoiceFileController {

    private final UploadInvoiceFileUseCase uploadInvoiceFileUseCase;
    private final InvoiceWebMapper invoiceWebMapper;

    public InvoiceFileController(UploadInvoiceFileUseCase uploadInvoiceFileUseCase,
                                 InvoiceWebMapper invoiceWebMapper) {
        this.uploadInvoiceFileUseCase = uploadInvoiceFileUseCase;
        this.invoiceWebMapper = invoiceWebMapper;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<InvoiceFileResponse> uploadInvoiceFile(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId,
            @RequestPart("file") MultipartFile file,
            @Valid @RequestPart("metadata") UploadInvoiceFileRequest metadata) throws IOException {

        InvoiceFileResponse response = invoiceWebMapper.toFileResponse(
                uploadInvoiceFileUseCase.uploadInvoiceFile(
                        invoiceId,
                        companyId,
                        file.getBytes(),
                        file.getOriginalFilename(),
                        file.getContentType(),
                        metadata.getFileType()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
