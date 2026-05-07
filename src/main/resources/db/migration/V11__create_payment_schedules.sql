CREATE TABLE payment_schedules (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id          BIGINT NOT NULL,
    created_by_user_id  BIGINT NULL,
    updated_by_user_id  BIGINT NULL,
    scheduled_date      DATE NOT NULL,
    amount              DECIMAL(15,2) NOT NULL,
    priority            ENUM('BAJA', 'MEDIA', 'ALTA', 'CRITICA') DEFAULT 'MEDIA',
    status              ENUM('PENDIENTE', 'PROGRAMADO', 'PAGADO', 'CANCELADO') DEFAULT 'PENDIENTE',
    notes               TEXT,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP NULL DEFAULT NULL,

    CONSTRAINT fk_pago_factura
        FOREIGN KEY (invoice_id) REFERENCES invoices(id),

    CONSTRAINT fk_pago_created_by
        FOREIGN KEY (created_by_user_id) REFERENCES company_users(id),

    CONSTRAINT fk_pago_updated_by
        FOREIGN KEY (updated_by_user_id) REFERENCES company_users(id),

    CONSTRAINT chk_monto_pago
        CHECK (amount > 0)
) ENGINE=InnoDB;
