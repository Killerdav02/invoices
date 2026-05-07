package com.amanga.invoices.infrastructure.adapter.out.persistence.adapter;

import com.amanga.invoices.application.port.out.SupplierRepositoryPort;
import com.amanga.invoices.domain.model.Supplier;
import com.amanga.invoices.infrastructure.adapter.out.persistence.mapper.SupplierPersistenceMapper;
import com.amanga.invoices.infrastructure.adapter.out.persistence.repository.SpringDataSupplierRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SupplierPersistenceAdapter implements SupplierRepositoryPort {

    private final SpringDataSupplierRepository repository;
    private final SupplierPersistenceMapper mapper;

    public SupplierPersistenceAdapter(SpringDataSupplierRepository repository,
                                      SupplierPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Supplier> findById(Long supplierId) {
        return repository.findByIdAndDeletedAtIsNull(supplierId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Supplier> findByTaxIdAndCompanyId(String taxId, Long companyId) {
        return repository.findByCompanyIdAndTaxId(companyId, taxId)
                .map(mapper::toDomain);
    }

    @Override
    public List<Supplier> findAllByCompanyId(Long companyId) {
        return repository.findAllByCompanyIdAndDeletedAtIsNull(companyId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Supplier save(Supplier supplier) {
        return mapper.toDomain(repository.save(mapper.toEntity(supplier)));
    }

    @Override
    public void softDelete(Long supplierId) {
        repository.findByIdAndDeletedAtIsNull(supplierId).ifPresent(entity -> {
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
        });
    }

    @Override
    public boolean existsByTaxIdAndCompanyId(String taxId, Long companyId) {
        return repository.findByCompanyIdAndTaxId(companyId, taxId).isPresent();
    }
}
