package com.amanga.invoices.infrastructure.adapter.out.persistence.adapter;

import com.amanga.invoices.application.port.out.CompanyUserRepositoryPort;
import com.amanga.invoices.domain.model.CompanyUser;
import com.amanga.invoices.infrastructure.adapter.out.persistence.mapper.CompanyUserPersistenceMapper;
import com.amanga.invoices.infrastructure.adapter.out.persistence.repository.SpringDataCompanyUserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CompanyUserPersistenceAdapter implements CompanyUserRepositoryPort {

    private final SpringDataCompanyUserRepository repository;
    private final CompanyUserPersistenceMapper mapper;

    public CompanyUserPersistenceAdapter(SpringDataCompanyUserRepository repository,
                                         CompanyUserPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<CompanyUser> findById(Long companyUserId) {
        return repository.findById(companyUserId)
                .filter(e -> e.getDeletedAt() == null)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<CompanyUser> findByAuth0UserId(String auth0UserId) {
        return repository.findByAuth0UserId(auth0UserId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<CompanyUser> findByEmailAndCompanyId(String email, Long companyId) {
        return repository.findByCompanyIdAndEmail(companyId, email)
                .map(mapper::toDomain);
    }

    @Override
    public List<CompanyUser> findAllByCompanyId(Long companyId) {
        return repository.findAllByCompanyIdAndDeletedAtIsNull(companyId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyUser save(CompanyUser companyUser) {
        return mapper.toDomain(repository.save(mapper.toEntity(companyUser)));
    }

    @Override
    public void softDelete(Long companyUserId) {
        repository.findById(companyUserId).ifPresent(entity -> {
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
        });
    }

    @Override
    public boolean existsByEmailAndCompanyId(String email, Long companyId) {
        return repository.findByCompanyIdAndEmail(companyId, email).isPresent();
    }
}
