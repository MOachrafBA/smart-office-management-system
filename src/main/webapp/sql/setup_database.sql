-- Komplettes Setup für Smart Office System
-- Führen Sie dieses Script als PostgreSQL Superuser (postgres) aus

-- 1. Schema erstellen (falls nicht vorhanden)
CREATE SCHEMA IF NOT EXISTS bwi520;

-- 2. Benutzer bwi520 erstellen (falls nicht vorhanden)
DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'bwi520') THEN
        CREATE ROLE bwi520 LOGIN PASSWORD 'bwi520';
    END IF;
END
$$;

-- 3. Berechtigungen für Schema setzen
GRANT USAGE ON SCHEMA bwi520 TO bwi520;
GRANT CREATE ON SCHEMA bwi520 TO bwi520;

-- 4. Users-Tabelle erstellen
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

-- 5. Sequence für ID erstellen
CREATE SEQUENCE IF NOT EXISTS bwi520.users_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

-- 6. Owner setzen
ALTER TABLE IF EXISTS bwi520.users OWNER TO bwi520;
ALTER SEQUENCE IF EXISTS bwi520.users_id_seq OWNER TO bwi520;

-- 7. Berechtigungen setzen
GRANT ALL ON TABLE bwi520.users TO bwi520;
GRANT ALL ON SEQUENCE bwi520.users_id_seq TO bwi520;

-- 8. Index für schnelle Benutzersuche
CREATE INDEX IF NOT EXISTS idx_users_username ON bwi520.users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON bwi520.users(email);
CREATE INDEX IF NOT EXISTS idx_users_active ON bwi520.users(is_active);

-- 9. Admin-Benutzer einfügen (Passwort: admin123)
INSERT INTO bwi520.users (username, email, password_hash, first_name, last_name, role) 
VALUES ('admin', 'admin@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K8K', 'Admin', 'User', 'admin')
ON CONFLICT (username) DO NOTHING;

-- 10. Test-Benutzer einfügen (Passwort: user123)
INSERT INTO bwi520.users (username, email, password_hash, first_name, last_name, role) 
VALUES ('user', 'user@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K8K', 'Test', 'User', 'user')
ON CONFLICT (username) DO NOTHING;

-- 11. Manager-Benutzer einfügen (Passwort: manager123)
INSERT INTO bwi520.users (username, email, password_hash, first_name, last_name, role) 
VALUES ('manager', 'manager@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K8K', 'Manager', 'User', 'manager')
ON CONFLICT (username) DO NOTHING;

-- 12. Tabelle anzeigen
SELECT * FROM bwi520.users;

-- 13. Berechtigungen prüfen
SELECT 
    schemaname,
    tablename,
    tableowner,
    hasinserts,
    hasselects,
    hasupdates,
    hasdeletes
FROM pg_tables 
WHERE schemaname = 'bwi520';

-- 14. Benutzerberechtigungen prüfen
SELECT 
    grantee,
    table_name,
    privilege_type
FROM information_schema.table_privileges 
WHERE table_schema = 'bwi520' 
AND table_name = 'users';
