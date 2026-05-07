CREATE TABLE suppliers (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id          BIGINT NOT NULL,
    created_by_user_id  BIGINT NULL,
    name                VARCHAR(255) NOT NULL,
    tax_id              VARCHAR(50) NOT NULL,
    email               VARCHAR(255),
    phone               VARCHAR(50),
    country_code        CHAR(2),
    supplier_type       ENUM('PERSONA_FISICA', 'PERSONA_MORAL') DEFAULT 'PERSONA_MORAL',
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP NULL DEFAULT NULL,

    CONSTRAINT fk_supplier_company
        FOREIGN KEY (company_id) REFERENCES companies(id),

    CONSTRAINT fk_supplier_created_by
        FOREIGN KEY (created_by_user_id) REFERENCES company_users(id),

    CONSTRAINT uq_supplier_tax_por_empresa
        UNIQUE (company_id, tax_id)
) ENGINE=InnoDB;
