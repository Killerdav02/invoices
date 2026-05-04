-- =========================================
-- EMPRESAS / TENANTS
-- =========================================
CREATE TABLE companies (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    tax_id        VARCHAR(50),
    country_code  CHAR(2) NOT NULL,
    currency      CHAR(3) NOT NULL DEFAULT 'USD',
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at    TIMESTAMP NULL DEFAULT NULL
) ENGINE=InnoDB;

-- =========================================
-- USUARIOS INTERNOS DE LA APP
-- Auth0 autentica, MySQL guarda company_id y role
-- =========================================
CREATE TABLE company_users (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id      BIGINT NOT NULL,
    auth0_user_id   VARCHAR(128) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    name            VARCHAR(255),
    role            ENUM('ADMIN', 'EMPLOYEE') NOT NULL DEFAULT 'EMPLOYEE',
    status          ENUM('INVITED', 'ACTIVE', 'DISABLED') NOT NULL DEFAULT 'ACTIVE',
    last_login_at   TIMESTAMP NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at      TIMESTAMP NULL DEFAULT NULL,

    CONSTRAINT fk_company_user_company
        FOREIGN KEY (company_id) REFERENCES companies(id),

    CONSTRAINT uq_company_user_auth0
        UNIQUE (company_id, auth0_user_id),

    CONSTRAINT uq_company_user_email
        UNIQUE (company_id, email)
) ENGINE=InnoDB;

-- =========================================
-- PROVEEDORES
-- =========================================
CREATE TABLE suppliers (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id          BIGINT NOT NULL,
    created_by_user_id  BIGINT NULL,
    name                VARCHAR(255) NOT NULL,
    tax_id              VARCHAR(50) NOT NULL,
    email               VARCHAR(255),
    phone               VARCHAR(50),
    country_code        CHAR(2),
    supplier_type       ENUM('PERSONA_FISICA', 'PERSONA_MORAL')
                        DEFAULT 'PERSONA_MORAL',
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

-- =========================================
-- FACTURAS
-- =========================================
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

-- =========================================
-- PARTIDAS DE FACTURA
-- =========================================
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

-- =========================================
-- FUENTES DE FACTURA
-- =========================================
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

-- =========================================
-- ARCHIVOS DE FACTURA
-- =========================================
CREATE TABLE invoice_files (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id           BIGINT NOT NULL,
    uploaded_by_user_id  BIGINT NULL,
    file_type            ENUM('PDF', 'XML', 'PNG', 'JPG', 'OTRO') NOT NULL,
    storage_provider     ENUM('S3', 'GCS', 'LOCAL', 'AZURE') NOT NULL DEFAULT 'S3',
    storage_bucket       VARCHAR(255),
    file_path            VARCHAR(1000) NOT NULL,
    file_size_bytes      BIGINT,
    checksum             VARCHAR(64),
    uploaded_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_archivo_factura
        FOREIGN KEY (invoice_id) REFERENCES invoices(id),

    CONSTRAINT fk_archivo_uploaded_by
        FOREIGN KEY (uploaded_by_user_id) REFERENCES company_users(id),

    CONSTRAINT chk_file_size
        CHECK (file_size_bytes IS NULL OR file_size_bytes >= 0)
) ENGINE=InnoDB;

-- =========================================
-- VALIDACIONES DE FACTURA
-- =========================================
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

-- =========================================
-- HISTORIAL DE ESTADOS
-- =========================================
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

-- =========================================
-- RECHAZOS DE FACTURA
-- =========================================
CREATE TABLE invoice_rejections (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id         BIGINT NOT NULL,
    status_history_id  BIGINT NOT NULL,
    created_by_user_id BIGINT NULL,
    reason             TEXT,
    feedback_sent      BOOLEAN DEFAULT FALSE,
    feedback_sent_at   TIMESTAMP NULL,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_rechazo_factura
        FOREIGN KEY (invoice_id) REFERENCES invoices(id),

    CONSTRAINT fk_rechazo_historial
        FOREIGN KEY (status_history_id) REFERENCES invoice_status_history(id),

    CONSTRAINT fk_rechazo_created_by
        FOREIGN KEY (created_by_user_id) REFERENCES company_users(id)
) ENGINE=InnoDB;

-- =========================================
-- PROGRAMACIÓN DE PAGOS
-- =========================================
CREATE TABLE payment_schedules (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id          BIGINT NOT NULL,
    created_by_user_id  BIGINT NULL,
    updated_by_user_id  BIGINT NULL,
    scheduled_date      DATE NOT NULL,
    amount              DECIMAL(15,2) NOT NULL,
    priority            ENUM('BAJA', 'MEDIA', 'ALTA', 'CRITICA') DEFAULT 'MEDIA',
    status              ENUM('PENDIENTE', 'PROGRAMADO', 'PAGADO', 'CANCELADO')
                        DEFAULT 'PENDIENTE',
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

-- =========================================
-- ÍNDICES
-- =========================================

CREATE INDEX idx_companies_deleted ON companies(deleted_at);

CREATE INDEX idx_company_users_company ON company_users(company_id);
CREATE INDEX idx_company_users_auth0 ON company_users(auth0_user_id);
CREATE INDEX idx_company_users_email ON company_users(email);
CREATE INDEX idx_company_users_role ON company_users(company_id, role);
CREATE INDEX idx_company_users_status ON company_users(company_id, status);

CREATE INDEX idx_proveedores_empresa ON suppliers(company_id);
CREATE INDEX idx_suppliers_created_by ON suppliers(created_by_user_id);
CREATE INDEX idx_suppliers_deleted ON suppliers(company_id, deleted_at);

CREATE INDEX idx_facturas_empresa_estado ON invoices(company_id, current_status);
CREATE INDEX idx_facturas_vencimiento ON invoices(company_id, due_date);
CREATE INDEX idx_facturas_proveedor ON invoices(supplier_id);
CREATE INDEX idx_facturas_actualizacion ON invoices(updated_at);
CREATE INDEX idx_facturas_created_by ON invoices(created_by_user_id);
CREATE INDEX idx_facturas_approved_by ON invoices(approved_by_user_id);
CREATE INDEX idx_facturas_deleted ON invoices(company_id, deleted_at);

CREATE INDEX idx_line_items_invoice ON invoice_line_items(invoice_id);

CREATE INDEX idx_sources_invoice ON invoice_sources(invoice_id);
CREATE INDEX idx_sources_type ON invoice_sources(source_type);

CREATE INDEX idx_archivos_tipo ON invoice_files(invoice_id, file_type);
CREATE INDEX idx_archivos_uploaded_by ON invoice_files(uploaded_by_user_id);

CREATE INDEX idx_validaciones_estado ON invoice_validations(invoice_id, status);
CREATE INDEX idx_validaciones_user ON invoice_validations(validated_by_user_id);

CREATE INDEX idx_historial_fecha ON invoice_status_history(invoice_id, changed_at);
CREATE INDEX idx_historial_changed_by ON invoice_status_history(changed_by_user_id);

CREATE INDEX idx_rejections_invoice ON invoice_rejections(invoice_id);
CREATE INDEX idx_rejections_history ON invoice_rejections(status_history_id);

CREATE INDEX idx_pagos_fecha_estado ON payment_schedules(scheduled_date, status);
CREATE INDEX idx_pagos_factura ON payment_schedules(invoice_id);
CREATE INDEX idx_pagos_created_by ON payment_schedules(created_by_user_id);
CREATE INDEX idx_pagos_updated_by ON payment_schedules(updated_by_user_id);
CREATE INDEX idx_pagos_deleted ON payment_schedules(invoice_id, deleted_at);
