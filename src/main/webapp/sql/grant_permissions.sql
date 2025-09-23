-- Berechtigungen für bwi520 Schema und users Tabelle
-- Führen Sie dieses Script als PostgreSQL Superuser (postgres) aus

-- Schema-Berechtigungen
GRANT USAGE ON SCHEMA bwi520 TO PUBLIC;
GRANT CREATE ON SCHEMA bwi520 TO PUBLIC;

-- Tabellen-Berechtigungen
GRANT SELECT, INSERT, UPDATE, DELETE ON bwi520.users TO PUBLIC;
GRANT USAGE, SELECT ON SEQUENCE bwi520.users_id_seq TO PUBLIC;

-- Indizes-Berechtigungen (falls nötig)
GRANT SELECT ON bwi520.users TO PUBLIC;

-- Alternative: Spezifische Benutzerberechtigungen
-- GRANT ALL PRIVILEGES ON SCHEMA bwi520 TO your_username;
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA bwi520 TO your_username;
-- GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA bwi520 TO your_username;

-- Berechtigungen anzeigen
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

-- Benutzerberechtigungen prüfen
SELECT 
    grantee,
    table_name,
    privilege_type
FROM information_schema.table_privileges 
WHERE table_schema = 'bwi520' 
AND table_name = 'users';
