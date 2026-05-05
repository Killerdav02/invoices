package com.amanga.invoices.application.port.out;

import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.domain.model.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepositoryPort {

    // Devuelve Optional — el Service decide si lanzar la excepción
    Optional<Invoice> findById(Long invoiceId);

    // Control de duplicados — por número de factura y proveedor dentro de una empresa
    Optional<Invoice> findByInvoiceNumberAndSupplierIdAndCompanyId(
            String invoiceNumber, Long supplierId, Long companyId);

    // Listar facturas de una empresa — siempre filtrado por companyId
    List<Invoice> findAllByCompanyId(Long companyId, int page, int size);

    // Listar facturas por estado dentro de una empresa
    List<Invoice> findAllByCompanyIdAndStatus(Long companyId, InvoiceStatus status, int page, int size);

    // Listar facturas de un proveedor dentro de una empresa
    List<Invoice> findAllByCompanyIdAndSupplierId(Long companyId, Long supplierId, int page, int size);

    // Maneja tanto insert como update
    Invoice save(Invoice invoice);

    // Soft delete — marca deleted_at, no borra el registro
    void softDelete(Long invoiceId);

    // Verificar duplicado sin cargar el objeto completo
    boolean existsByInvoiceNumberAndSupplierIdAndCompanyId(
            String invoiceNumber, Long supplierId, Long companyId);
}
