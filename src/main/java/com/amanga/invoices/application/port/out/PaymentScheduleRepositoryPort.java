package com.amanga.invoices.application.port.out;

import com.amanga.invoices.domain.enums.PaymentScheduleStatus;
import com.amanga.invoices.domain.model.PaymentSchedule;

import java.util.List;
import java.util.Optional;

public interface PaymentScheduleRepositoryPort {

    // Devuelve Optional — el Service decide si lanzar la excepción
    Optional<PaymentSchedule> findById(Long paymentScheduleId);

    // Listar todos los pagos programados de una factura
    List<PaymentSchedule> findAllByInvoiceId(Long invoiceId);

    // Listar pagos programados de una factura por estado
    List<PaymentSchedule> findAllByInvoiceIdAndStatus(Long invoiceId, PaymentScheduleStatus status);

    // Maneja tanto insert como update
    PaymentSchedule save(PaymentSchedule paymentSchedule);

    // Soft delete — marca deleted_at, no borra el registro
    void softDelete(Long paymentScheduleId);
}
