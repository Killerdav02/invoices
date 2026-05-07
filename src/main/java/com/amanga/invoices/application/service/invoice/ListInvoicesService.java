package com.amanga.invoices.application.service.invoice;

import com.amanga.invoices.application.port.in.invoice.ListInvoicesUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Invoice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListInvoicesService implements ListInvoicesUseCase {

    private final InvoiceRepositoryPort invoiceRepository;
    private final CurrentUserProviderPort currentUserProvider;

    public ListInvoicesService(InvoiceRepositoryPort invoiceRepository,
                               CurrentUserProviderPort currentUserProvider) {
        this.invoiceRepository = invoiceRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public List<Invoice> listByCompany(Long companyId, int page, int size) {
        CurrentUser currentUser = currentUserProvider.getCurrentUser();
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }
        return invoiceRepository.findAllByCompanyId(companyId, page, size);
    }

    @Override
    public List<Invoice> listByStatus(Long companyId, InvoiceStatus status, int page, int size) {
        CurrentUser currentUser = currentUserProvider.getCurrentUser();
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }
        return invoiceRepository.findAllByCompanyIdAndStatus(companyId, status, page, size);
    }

    @Override
    public List<Invoice> listBySupplier(Long companyId, Long supplierId, int page, int size) {
        CurrentUser currentUser = currentUserProvider.getCurrentUser();
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }
        return invoiceRepository.findAllByCompanyIdAndSupplierId(companyId, supplierId, page, size);
    }
}
