package com.amanga.invoices.infrastructure.adapter.out.persistence.adapter;

import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.infrastructure.adapter.out.persistence.mapper.InvoicePersistenceMapper;
import com.amanga.invoices.infrastructure.adapter.out.persistence.repository.SpringDataInvoiceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InvoicePersistenceAdapter implements InvoiceRepositoryPort {

    private final SpringDataInvoiceRepository repository;
    private final InvoicePersistenceMapper mapper;

    public InvoicePersistenceAdapter(SpringDataInvoiceRepository repository,
                                     InvoicePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Invoice> findById(Long invoiceId) {
        return repository.findByIdAndDeletedAtIsNull(invoiceId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Invoice> findByInvoiceNumberAndSupplierIdAndCompanyId(
            String invoiceNumber, Long supplierId, Long companyId) {
        return repository.findByCompanyIdAndSupplierIdAndInvoiceNumber(companyId, supplierId, invoiceNumber)
                .map(mapper::toDomain);
    }

    @Override
    public List<Invoice> findAllByCompanyId(Long companyId, int page, int size) {
        return repository.findAllByCompanyIdAndDeletedAtIsNull(companyId, PageRequest.of(page, size))
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Invoice> findAllByCompanyIdAndStatus(Long companyId, InvoiceStatus status, int page, int size) {
        return repository.findAllByCompanyIdAndCurrentStatusAndDeletedAtIsNull(
                        companyId, status, PageRequest.of(page, size))
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Invoice> findAllByCompanyIdAndSupplierId(Long companyId, Long supplierId, int page, int size) {
        return repository.findAllByCompanyIdAndSupplierIdAndDeletedAtIsNull(
                        companyId, supplierId, PageRequest.of(page, size))
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Invoice save(Invoice invoice) {
        return mapper.toDomain(repository.save(mapper.toEntity(invoice)));
    }

    @Override
    public void softDelete(Long invoiceId) {
        repository.findByIdAndDeletedAtIsNull(invoiceId).ifPresent(entity -> {
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
        });
    }

    @Override
    public boolean existsByInvoiceNumberAndSupplierIdAndCompanyId(
            String invoiceNumber, Long supplierId, Long companyId) {
        return repository.findByCompanyIdAndSupplierIdAndInvoiceNumber(companyId, supplierId, invoiceNumber)
                .isPresent();
    }
}
