package com.amanga.invoices.infrastructure.adapter.out.persistence.adapter;

import com.amanga.invoices.application.port.out.InvoiceRejectionRepositoryPort;
import com.amanga.invoices.domain.model.InvoiceRejection;
import com.amanga.invoices.infrastructure.adapter.out.persistence.mapper.InvoiceRejectionPersistenceMapper;
import com.amanga.invoices.infrastructure.adapter.out.persistence.repository.SpringDataInvoiceRejectionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class InvoiceRejectionPersistenceAdapter implements InvoiceRejectionRepositoryPort {

    private final SpringDataInvoiceRejectionRepository repository;
    private final InvoiceRejectionPersistenceMapper mapper;

    public InvoiceRejectionPersistenceAdapter(SpringDataInvoiceRejectionRepository repository,
                                              InvoiceRejectionPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<InvoiceRejection> findByInvoiceId(Long invoiceId) {
        return repository.findFirstByInvoiceIdOrderByCreatedAtDesc(invoiceId)
                .map(mapper::toDomain);
    }

    @Override
    public InvoiceRejection save(InvoiceRejection invoiceRejection) {
        return mapper.toDomain(repository.save(mapper.toEntity(invoiceRejection)));
    }

    @Override
    public void markFeedbackSent(Long invoiceRejectionId) {
        repository.findById(invoiceRejectionId).ifPresent(entity -> {
            entity.setFeedbackSent(true);
            entity.setFeedbackSentAt(LocalDateTime.now());
            repository.save(entity);
        });
    }
}
