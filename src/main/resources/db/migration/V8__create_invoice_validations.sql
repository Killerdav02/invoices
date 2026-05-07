CREATE TABLE invoice_validations (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id           BIGINT NOT NULL,
    validated_by_user_id BIGINT NULL,
    rule_name            VARCHAR(100) NOT NULL,
    status               ENUM(
                             'PENDIENTE',
                             'APROBADA',
                             'ADVERTENCIA',
                             'FALLIDA'
                         ) NOT NULL DEFAULT 'PENDIENTE',
    message              TEXT,
    validated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_validacion_factura
        FOREIGN KEY (invoice_id) REFERENCES invoices(id),

    CONSTRAINT fk_validacion_user
        FOREIGN KEY (validated_by_user_id) REFERENCES company_users(id)
) ENGINE=InnoDB;
