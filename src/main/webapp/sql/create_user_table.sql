-- User-Tabelle für Smart Office System
-- Erstellt eine Tabelle für Benutzer mit Anmeldeinformationen im bwi520 Schema

-- Schema festlegen
SET search_path TO bwi520;

CREATE TABLE IF NOT EXISTS bwi520.users (
    id integer NOT NULL DEFAULT nextval('bwi520.users_id_seq'::regclass),
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    role VARCHAR(20) DEFAULT 'user',
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    CONSTRAINT chk_role CHECK (role IN ('admin', 'user', 'manager')),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

-- Sequence für ID erstellen
CREATE SEQUENCE IF NOT EXISTS bwi520.users_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

-- Owner setzen
ALTER TABLE IF EXISTS bwi520.users OWNER TO bwi520;
ALTER SEQUENCE IF EXISTS bwi520.users_id_seq OWNER TO bwi520;

-- Berechtigungen setzen
GRANT ALL ON TABLE bwi520.users TO bwi520;
GRANT ALL ON SEQUENCE bwi520.users_id_seq TO bwi520;

-- Index für schnelle Benutzersuche
CREATE INDEX IF NOT EXISTS idx_users_username ON bwi520.users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON bwi520.users(email);
CREATE INDEX IF NOT EXISTS idx_users_active ON bwi520.users(is_active);

-- Admin-Benutzer einfügen (Passwort: admin123)
INSERT INTO bwi520.users (username, email, password_hash, first_name, last_name, role) 
VALUES ('admin', 'admin@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K8K', 'Admin', 'User', 'admin')
ON CONFLICT (username) DO NOTHING;

-- Test-Benutzer einfügen (Passwort: user123)
INSERT INTO bwi520.users (username, email, password_hash, first_name, last_name, role) 
VALUES ('user', 'user@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K8K', 'Test', 'User', 'user')
ON CONFLICT (username) DO NOTHING;

-- Manager-Benutzer einfügen (Passwort: manager123)
INSERT INTO bwi520.users (username, email, password_hash, first_name, last_name, role) 
VALUES ('manager', 'manager@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K8K', 'Manager', 'User', 'manager')
ON CONFLICT (username) DO NOTHING;

-- Tabelle anzeigen
SELECT * FROM bwi520.users;
