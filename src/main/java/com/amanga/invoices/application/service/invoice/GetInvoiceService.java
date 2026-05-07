package com.amanga.invoices.application.service.invoice;

import com.amanga.invoices.application.port.in.invoice.GetInvoiceUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.InvoiceNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Invoice;
import org.springframework.stereotype.Service;

@Service
public class GetInvoiceService implements GetInvoiceUseCase {

    private final InvoiceRepositoryPort invoiceRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public GetInvoiceService(InvoiceRepositoryPort invoiceRepository,
                             CurrentUserProviderPort currentUserProvider) {
        this.invoiceRepository = invoiceRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public Invoice getInvoice(Long invoiceId, Long companyId) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Find invoice
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

        // 4. Validate invoice belongs to the company
        if (!invoice.getCompanyId().equals(companyId)) {
            throw new ForbiddenActionException(
                    "Invoice " + invoiceId + " does not belong to company " + companyId);
        }

        return invoice;
    }
}
