package com.amanga.invoices.infrastructure.adapter.out.persistence.repository;

import com.amanga.invoices.domain.enums.PaymentScheduleStatus;
import com.amanga.invoices.infrastructure.adapter.out.persistence.entity.PaymentScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpringDataPaymentScheduleRepository extends JpaRepository<PaymentScheduleJpaEntity, Long> {

    List<PaymentScheduleJpaEntity> findAllByInvoiceIdAndDeletedAtIsNull(Long invoiceId);

    List<PaymentScheduleJpaEntity> findAllByInvoiceIdAndStatusAndDeletedAtIsNull(
            Long invoiceId, PaymentScheduleStatus status);

    List<PaymentScheduleJpaEntity> findAllByScheduledDateAndStatusAndDeletedAtIsNull(
            LocalDate scheduledDate, PaymentScheduleStatus status);

    List<PaymentScheduleJpaEntity> findAllByScheduledDateBeforeAndStatusAndDeletedAtIsNull(
            LocalDate date, PaymentScheduleStatus status);
}
