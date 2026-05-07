package com.amanga.invoices.infrastructure.adapter.out.persistence.adapter;

import com.amanga.invoices.application.port.out.InvoiceValidationRepositoryPort;
import com.amanga.invoices.domain.enums.InvoiceValidationStatus;
import com.amanga.invoices.domain.model.InvoiceValidation;
import com.amanga.invoices.infrastructure.adapter.out.persistence.mapper.InvoiceValidationPersistenceMapper;
import com.amanga.invoices.infrastructure.adapter.out.persistence.repository.SpringDataInvoiceValidationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceValidationPersistenceAdapter implements InvoiceValidationRepositoryPort {

    private final SpringDataInvoiceValidationRepository repository;
    private final InvoiceValidationPersistenceMapper mapper;

    public InvoiceValidationPersistenceAdapter(SpringDataInvoiceValidationRepository repository,
                                               InvoiceValidationPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<InvoiceValidation> findAllByInvoiceId(Long invoiceId) {
        return repository.findAllByInvoiceId(invoiceId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceValidation> findAllByInvoiceIdAndStatus(Long invoiceId, InvoiceValidationStatus status) {
        return repository.findAllByInvoiceIdAndStatus(invoiceId, status)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceValidation save(InvoiceValidation invoiceValidation) {
        return mapper.toDomain(repository.save(mapper.toEntity(invoiceValidation)));
    }

    @Override
    public boolean existsFailedByInvoiceId(Long invoiceId) {
        return repository.existsByInvoiceIdAndStatus(invoiceId, InvoiceValidationStatus.FALLIDA);
    }
}
