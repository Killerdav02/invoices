package com.amanga.invoices.application.service.invoice;

import com.amanga.invoices.application.port.in.invoice.RegisterInvoiceSourceUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceSourceRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.InvoiceNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.domain.model.InvoiceSource;
import org.springframework.stereotype.Service;

@Service
public class RegisterInvoiceSourceService implements RegisterInvoiceSourceUseCase {

    private final InvoiceRepositoryPort invoiceRepository;
    private final InvoiceSourceRepositoryPort invoiceSourceRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public RegisterInvoiceSourceService(InvoiceRepositoryPort invoiceRepository,
                                        InvoiceSourceRepositoryPort invoiceSourceRepository,
                                        CurrentUserProviderPort currentUserProvider) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceSourceRepository = invoiceSourceRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public InvoiceSource registerInvoiceSource(InvoiceSource invoiceSource) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Find invoice to resolve the company
        Invoice invoice = invoiceRepository.findById(invoiceSource.getInvoiceId())
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceSource.getInvoiceId()));

        // 3. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(invoice.getCompanyId())) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), invoice.getCompanyId());
        }

        // 4. Validate current user is ADMIN
        if (!currentUser.isAdmin()) {
            throw new ForbiddenActionException("Only admins can register invoice sources");
        }

        // 5. Persist and return
        return invoiceSourceRepository.save(invoiceSource);
    }
}
