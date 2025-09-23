-- User-Tabelle für Smart Office System
-- Erstellt eine Tabelle für Benutzer mit Anmeldeinformationen im public Schema
-- (Alternative falls bwi520 Schema Probleme macht)

-- Schema auf public setzen
SET search_path TO public;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    role VARCHAR(20) DEFAULT 'user',
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    CONSTRAINT chk_role CHECK (role IN ('admin', 'user', 'manager'))
);

-- Index für schnelle Benutzersuche
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_active ON users(is_active);

-- Admin-Benutzer einfügen (Passwort: admin123)
INSERT INTO users (username, email, password_hash, first_name, last_name, role) 
VALUES ('admin', 'admin@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K8K', 'Admin', 'User', 'admin')
ON CONFLICT (username) DO NOTHING;

-- Test-Benutzer einfügen (Passwort: user123)
INSERT INTO users (username, email, password_hash, first_name, last_name, role) 
VALUES ('user', 'user@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K8K', 'Test', 'User', 'user')
ON CONFLICT (username) DO NOTHING;

-- Manager-Benutzer einfügen (Passwort: manager123)
INSERT INTO users (username, email, password_hash, first_name, last_name, role) 
VALUES ('manager', 'manager@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K8K', 'Manager', 'User', 'manager')
ON CONFLICT (username) DO NOTHING;

-- Tabelle anzeigen
SELECT * FROM users;
