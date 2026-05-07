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
