CREATE TABLE invoice_status_history (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id           BIGINT NOT NULL,
    changed_by_user_id   BIGINT NULL,
    status               ENUM(
                             'RECIBIDA',
                             'EN_REVISION',
                             'APROBADA',
                             'RECHAZADA',
                             'PROGRAMADA',
                             'PAGADA',
                             'CANCELADA'
                         ) NOT NULL,
    changed_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changed_by_snapshot  VARCHAR(255),
    notes                TEXT,

    CONSTRAINT fk_historial_factura
        FOREIGN KEY (invoice_id) REFERENCES invoices(id),

    CONSTRAINT fk_historial_changed_by
        FOREIGN KEY (changed_by_user_id) REFERENCES company_users(id)
) ENGINE=InnoDB;
