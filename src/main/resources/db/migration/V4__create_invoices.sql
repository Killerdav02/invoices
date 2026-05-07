CREATE TABLE invoices (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id           BIGINT NOT NULL,
    supplier_id          BIGINT NOT NULL,
    created_by_user_id   BIGINT NULL,
    approved_by_user_id  BIGINT NULL,
    invoice_number       VARCHAR(100) NOT NULL,
    issue_date           DATE NOT NULL,
    due_date             DATE,
    subtotal             DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    tax_amount           DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    discount_amount      DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    total_amount         DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    currency             CHAR(3) NOT NULL DEFAULT 'USD',
    current_status       ENUM(
                             'RECIBIDA',
                             'EN_REVISION',
                             'APROBADA',
                             'RECHAZADA',
                             'PROGRAMADA',
                             'PAGADA',
                             'CANCELADA'
                         ) NOT NULL DEFAULT 'RECIBIDA',
    payment_terms        VARCHAR(50),
    notes                TEXT,
    approved_at          TIMESTAMP NULL,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at           TIMESTAMP NULL DEFAULT NULL,

    CONSTRAINT fk_invoice_company
        FOREIGN KEY (company_id) REFERENCES companies(id),

    CONSTRAINT fk_invoice_supplier
        FOREIGN KEY (supplier_id) REFERENCES suppliers(id),

    CONSTRAINT fk_invoice_created_by
        FOREIGN KEY (created_by_user_id) REFERENCES company_users(id),

    CONSTRAINT fk_invoice_approved_by
        FOREIGN KEY (approved_by_user_id) REFERENCES company_users(id),

    CONSTRAINT uq_factura_proveedor
        UNIQUE (company_id, supplier_id, invoice_number),

    CONSTRAINT chk_subtotal_positivo
        CHECK (subtotal >= 0),

    CONSTRAINT chk_tax_positivo
        CHECK (tax_amount >= 0),

    CONSTRAINT chk_discount_positivo
        CHECK (discount_amount >= 0),

    CONSTRAINT chk_monto_positivo
        CHECK (total_amount >= 0),

    CONSTRAINT chk_fechas
        CHECK (due_date IS NULL OR due_date >= issue_date)
) ENGINE=InnoDB;
