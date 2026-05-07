CREATE TABLE invoice_sources (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id        BIGINT NOT NULL,
    source_type       ENUM('EMAIL', 'WHATSAPP', 'MANUAL', 'API', 'OTRO') NOT NULL,
    source_reference  VARCHAR(255),
    metadata          JSON,
    received_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_fuente_factura
        FOREIGN KEY (invoice_id) REFERENCES invoices(id)
) ENGINE=InnoDB;
