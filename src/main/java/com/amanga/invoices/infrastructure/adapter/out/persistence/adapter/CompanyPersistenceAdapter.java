package com.amanga.invoices.infrastructure.adapter.out.persistence.adapter;

import com.amanga.invoices.application.port.out.CompanyRepositoryPort;
import com.amanga.invoices.domain.model.Company;
import com.amanga.invoices.infrastructure.adapter.out.persistence.mapper.CompanyPersistenceMapper;
import com.amanga.invoices.infrastructure.adapter.out.persistence.repository.SpringDataCompanyRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class CompanyPersistenceAdapter implements CompanyRepositoryPort {

    private final SpringDataCompanyRepository repository;
    private final CompanyPersistenceMapper mapper;

    public CompanyPersistenceAdapter(SpringDataCompanyRepository repository,
                                     CompanyPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Company> findById(Long companyId) {
        return repository.findByIdAndDeletedAtIsNull(companyId)
                .map(mapper::toDomain);
    }

    @Override
    public Company save(Company company) {
        return mapper.toDomain(repository.save(mapper.toEntity(company)));
    }

    @Override
    public void softDelete(Long companyId) {
        repository.findByIdAndDeletedAtIsNull(companyId).ifPresent(entity -> {
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
        });
    }

    @Override
    public boolean existsById(Long companyId) {
        return repository.existsById(companyId);
    }
}
