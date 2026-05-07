package com.amanga.invoices.infrastructure.adapter.out.persistence.adapter;

import com.amanga.invoices.application.port.out.InvoiceLineItemRepositoryPort;
import com.amanga.invoices.domain.model.InvoiceLineItem;
import com.amanga.invoices.infrastructure.adapter.out.persistence.mapper.InvoiceLineItemPersistenceMapper;
import com.amanga.invoices.infrastructure.adapter.out.persistence.repository.SpringDataInvoiceLineItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceLineItemPersistenceAdapter implements InvoiceLineItemRepositoryPort {

    private final SpringDataInvoiceLineItemRepository repository;

    public InvoiceLineItemPersistenceAdapter(SpringDataInvoiceLineItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<InvoiceLineItem> saveAll(List<InvoiceLineItem> lineItems) {
        return lineItems.stream()
                .map(InvoiceLineItemPersistenceMapper::toEntity)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        repository::saveAll
                ))
                .stream()
                .map(InvoiceLineItemPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceLineItem> findAllByInvoiceId(Long invoiceId) {
        return repository.findAllByInvoiceId(invoiceId)
                .stream()
                .map(InvoiceLineItemPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }
}
