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
