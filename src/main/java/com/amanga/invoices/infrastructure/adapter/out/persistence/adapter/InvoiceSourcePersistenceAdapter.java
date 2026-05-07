package com.amanga.invoices.infrastructure.adapter.out.persistence.adapter;

import com.amanga.invoices.application.port.out.InvoiceSourceRepositoryPort;
import com.amanga.invoices.domain.model.InvoiceSource;
import com.amanga.invoices.infrastructure.adapter.out.persistence.mapper.InvoiceSourcePersistenceMapper;
import com.amanga.invoices.infrastructure.adapter.out.persistence.repository.SpringDataInvoiceSourceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceSourcePersistenceAdapter implements InvoiceSourceRepositoryPort {

    private final SpringDataInvoiceSourceRepository repository;
    private final InvoiceSourcePersistenceMapper mapper;

    public InvoiceSourcePersistenceAdapter(SpringDataInvoiceSourceRepository repository,
                                           InvoiceSourcePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<InvoiceSource> findAllByInvoiceId(Long invoiceId) {
        return repository.findAllByInvoiceId(invoiceId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceSource save(InvoiceSource invoiceSource) {
        return mapper.toDomain(repository.save(mapper.toEntity(invoiceSource)));
    }
}
