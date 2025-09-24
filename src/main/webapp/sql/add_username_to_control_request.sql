-- Erweitert die control_request Tabelle um Benutzer-Informationen
-- Fügt eine username Spalte hinzu

-- Schema festlegen
SET search_path TO bwi520;

-- Username-Spalte zur control_request Tabelle hinzufügen
ALTER TABLE control_request 
ADD COLUMN IF NOT EXISTS username VARCHAR(100);

-- Index für schnelle Benutzer-Suche
CREATE INDEX IF NOT EXISTS idx_control_request_username ON control_request(username);

-- Berechtigungen setzen
GRANT ALL ON TABLE control_request TO bwi520;

-- Tabelle anzeigen
SELECT * FROM control_request ORDER BY created_at DESC LIMIT 10;
