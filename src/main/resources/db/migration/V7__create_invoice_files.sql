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
