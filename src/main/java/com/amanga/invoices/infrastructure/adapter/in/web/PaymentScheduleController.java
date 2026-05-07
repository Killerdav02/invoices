package com.amanga.invoices.infrastructure.adapter.in.web;

import com.amanga.invoices.application.port.in.payment.CancelPaymentScheduleUseCase;
import com.amanga.invoices.application.port.in.payment.ListPaymentSchedulesUseCase;
import com.amanga.invoices.application.port.in.payment.MarkPaymentAsPaidUseCase;
import com.amanga.invoices.application.port.in.payment.SchedulePaymentUseCase;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.payment.PaymentScheduleResponse;
import com.amanga.invoices.infrastructure.adapter.in.web.dto.payment.SchedulePaymentRequest;
import com.amanga.invoices.infrastructure.adapter.in.web.mapper.PaymentScheduleWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/companies/{companyId}/invoices/{invoiceId}/payments")
public class PaymentScheduleController {

    private final SchedulePaymentUseCase schedulePaymentUseCase;
    private final ListPaymentSchedulesUseCase listPaymentSchedulesUseCase;
    private final CancelPaymentScheduleUseCase cancelPaymentScheduleUseCase;
    private final MarkPaymentAsPaidUseCase markPaymentAsPaidUseCase;
    private final PaymentScheduleWebMapper paymentScheduleWebMapper;

    public PaymentScheduleController(SchedulePaymentUseCase schedulePaymentUseCase,
                                     ListPaymentSchedulesUseCase listPaymentSchedulesUseCase,
                                     CancelPaymentScheduleUseCase cancelPaymentScheduleUseCase,
                                     MarkPaymentAsPaidUseCase markPaymentAsPaidUseCase,
                                     PaymentScheduleWebMapper paymentScheduleWebMapper) {
        this.schedulePaymentUseCase = schedulePaymentUseCase;
        this.listPaymentSchedulesUseCase = listPaymentSchedulesUseCase;
        this.cancelPaymentScheduleUseCase = cancelPaymentScheduleUseCase;
        this.markPaymentAsPaidUseCase = markPaymentAsPaidUseCase;
        this.paymentScheduleWebMapper = paymentScheduleWebMapper;
    }

    @PostMapping
    public ResponseEntity<PaymentScheduleResponse> schedulePayment(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId,
            @Valid @RequestBody SchedulePaymentRequest request) {

        PaymentScheduleResponse response = paymentScheduleWebMapper.toResponse(
                schedulePaymentUseCase.schedulePayment(
                        paymentScheduleWebMapper.toDomain(request, invoiceId)
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PaymentScheduleResponse>> listPaymentSchedules(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId) {

        List<PaymentScheduleResponse> response = listPaymentSchedulesUseCase
                .listByInvoice(invoiceId, companyId)
                .stream()
                .map(paymentScheduleWebMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{paymentScheduleId}/pay")
    public ResponseEntity<PaymentScheduleResponse> markPaymentAsPaid(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId,
            @PathVariable Long paymentScheduleId) {

        return ResponseEntity.ok(
                paymentScheduleWebMapper.toResponse(
                        markPaymentAsPaidUseCase.markPaymentAsPaid(paymentScheduleId, companyId)
                )
        );
    }

    @DeleteMapping("/{paymentScheduleId}")
    public ResponseEntity<Void> cancelPaymentSchedule(
            @PathVariable Long companyId,
            @PathVariable Long invoiceId,
            @PathVariable Long paymentScheduleId) {

        cancelPaymentScheduleUseCase.cancelPaymentSchedule(paymentScheduleId, companyId);

        return ResponseEntity.noContent().build();
    }
}
