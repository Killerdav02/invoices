package com.amanga.invoices.infrastructure.adapter.out.persistence.adapter;

import com.amanga.invoices.application.port.out.InvoiceFileRepositoryPort;
import com.amanga.invoices.domain.model.InvoiceFile;
import com.amanga.invoices.infrastructure.adapter.out.persistence.mapper.InvoiceFilePersistenceMapper;
import com.amanga.invoices.infrastructure.adapter.out.persistence.repository.SpringDataInvoiceFileRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InvoiceFilePersistenceAdapter implements InvoiceFileRepositoryPort {

    private final SpringDataInvoiceFileRepository repository;
    private final InvoiceFilePersistenceMapper mapper;

    public InvoiceFilePersistenceAdapter(SpringDataInvoiceFileRepository repository,
                                         InvoiceFilePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<InvoiceFile> findById(Long invoiceFileId) {
        return repository.findById(invoiceFileId)
                .map(mapper::toDomain);
    }

    @Override
    public List<InvoiceFile> findAllByInvoiceId(Long invoiceId) {
        return repository.findAllByInvoiceId(invoiceId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceFile save(InvoiceFile invoiceFile) {
        return mapper.toDomain(repository.save(mapper.toEntity(invoiceFile)));
    }

    @Override
    public void delete(Long invoiceFileId) {
        repository.deleteById(invoiceFileId);
    }
}
