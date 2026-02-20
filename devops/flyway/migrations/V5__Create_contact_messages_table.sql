-- ============================================
-- V5__Create_contact_messages_table.sql
-- Table pour stocker les messages du formulaire de contact
-- ============================================

CREATE TABLE IF NOT EXISTS contact_messages (
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(100)  NOT NULL,
    last_name   VARCHAR(100)  NOT NULL,
    email       VARCHAR(255)  NOT NULL,
    phone       VARCHAR(30),
    company     VARCHAR(255),
    subject     VARCHAR(255)  NOT NULL,
    message     TEXT          NOT NULL,
    read        BOOLEAN       NOT NULL DEFAULT false,
    created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active   BOOLEAN       NOT NULL DEFAULT true
);

CREATE INDEX IF NOT EXISTS idx_contact_messages_email ON contact_messages (email);
CREATE INDEX IF NOT EXISTS idx_contact_messages_read  ON contact_messages (read);
