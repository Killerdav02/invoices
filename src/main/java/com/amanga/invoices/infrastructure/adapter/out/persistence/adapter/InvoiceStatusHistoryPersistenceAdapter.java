package com.amanga.invoices.infrastructure.adapter.out.persistence.adapter;

import com.amanga.invoices.application.port.out.InvoiceStatusHistoryRepositoryPort;
import com.amanga.invoices.domain.model.InvoiceStatusHistory;
import com.amanga.invoices.infrastructure.adapter.out.persistence.mapper.InvoiceStatusHistoryPersistenceMapper;
import com.amanga.invoices.infrastructure.adapter.out.persistence.repository.SpringDataInvoiceStatusHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceStatusHistoryPersistenceAdapter implements InvoiceStatusHistoryRepositoryPort {

    private final SpringDataInvoiceStatusHistoryRepository repository;
    private final InvoiceStatusHistoryPersistenceMapper mapper;

    public InvoiceStatusHistoryPersistenceAdapter(SpringDataInvoiceStatusHistoryRepository repository,
                                                  InvoiceStatusHistoryPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<InvoiceStatusHistory> findAllByInvoiceId(Long invoiceId) {
        return repository.findAllByInvoiceIdOrderByChangedAtAsc(invoiceId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceStatusHistory save(InvoiceStatusHistory invoiceStatusHistory) {
        return mapper.toDomain(repository.save(mapper.toEntity(invoiceStatusHistory)));
    }
}
