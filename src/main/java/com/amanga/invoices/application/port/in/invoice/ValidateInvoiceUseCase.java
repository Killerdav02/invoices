package com.amanga.invoices.application.port.in.invoice;

import com.amanga.invoices.domain.enums.InvoiceValidationStatus;
import com.amanga.invoices.domain.model.InvoiceValidation;

public interface ValidateInvoiceUseCase {

    InvoiceValidation validateInvoice(
            Long invoiceId,
            Long companyId,
            Long currentUserId,
            String validationRule,
            InvoiceValidationStatus status,
            String notes);
}
