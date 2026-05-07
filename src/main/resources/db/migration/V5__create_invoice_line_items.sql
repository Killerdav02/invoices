CREATE TABLE invoice_line_items (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id    BIGINT NOT NULL,
    description   VARCHAR(500),
    quantity      DECIMAL(10,4) NOT NULL DEFAULT 1.0000,
    unit_price    DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    tax_rate      DECIMAL(5,2)  NOT NULL DEFAULT 0.00,
    line_total    DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_partida_factura
        FOREIGN KEY (invoice_id) REFERENCES invoices(id),

    CONSTRAINT chk_line_quantity
        CHECK (quantity > 0),

    CONSTRAINT chk_line_unit_price
        CHECK (unit_price >= 0),

    CONSTRAINT chk_line_total
        CHECK (line_total >= 0)
) ENGINE=InnoDB;
