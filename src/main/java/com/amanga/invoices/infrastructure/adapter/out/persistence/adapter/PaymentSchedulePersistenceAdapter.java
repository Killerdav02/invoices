package com.amanga.invoices.infrastructure.adapter.out.persistence.adapter;

import com.amanga.invoices.application.port.out.PaymentScheduleRepositoryPort;
import com.amanga.invoices.domain.enums.PaymentScheduleStatus;
import com.amanga.invoices.domain.model.PaymentSchedule;
import com.amanga.invoices.infrastructure.adapter.out.persistence.mapper.PaymentSchedulePersistenceMapper;
import com.amanga.invoices.infrastructure.adapter.out.persistence.repository.SpringDataPaymentScheduleRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PaymentSchedulePersistenceAdapter implements PaymentScheduleRepositoryPort {

    private final SpringDataPaymentScheduleRepository repository;
    private final PaymentSchedulePersistenceMapper mapper;

    public PaymentSchedulePersistenceAdapter(SpringDataPaymentScheduleRepository repository,
                                             PaymentSchedulePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<PaymentSchedule> findById(Long paymentScheduleId) {
        return repository.findById(paymentScheduleId)
                .map(mapper::toDomain);
    }

    @Override
    public List<PaymentSchedule> findAllByInvoiceId(Long invoiceId) {
        return repository.findAllByInvoiceIdAndDeletedAtIsNull(invoiceId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentSchedule> findAllByInvoiceIdAndStatus(Long invoiceId, PaymentScheduleStatus status) {
        return repository.findAllByInvoiceIdAndStatusAndDeletedAtIsNull(invoiceId, status)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentSchedule save(PaymentSchedule paymentSchedule) {
        return mapper.toDomain(repository.save(mapper.toEntity(paymentSchedule)));
    }

    @Override
    public void softDelete(Long paymentScheduleId) {
        repository.findById(paymentScheduleId).ifPresent(entity -> {
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
        });
    }
}
