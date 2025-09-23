-- Rooms-Tabelle für Smart Office System
-- Erstellt eine Tabelle für Räume im bwi520 Schema

-- Schema festlegen
SET search_path TO bwi520;

-- Rooms-Tabelle erstellen
CREATE TABLE IF NOT EXISTS bwi520.rooms (
    id integer NOT NULL DEFAULT nextval('bwi520.rooms_id_seq'::regclass),
    floor_id integer NOT NULL,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT rooms_pkey PRIMARY KEY (id),
    CONSTRAINT fk_rooms_floor FOREIGN KEY (floor_id) REFERENCES bwi520.floors(id)
);

-- Sequence für ID erstellen
CREATE SEQUENCE IF NOT EXISTS bwi520.rooms_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

-- Owner setzen
ALTER TABLE IF EXISTS bwi520.rooms OWNER TO bwi520;
ALTER SEQUENCE IF EXISTS bwi520.rooms_id_seq OWNER TO bwi520;

-- Berechtigungen setzen
GRANT ALL ON TABLE bwi520.rooms TO bwi520;
GRANT ALL ON SEQUENCE bwi520.rooms_id_seq TO bwi520;

-- Index für schnelle Raumsuche
CREATE INDEX IF NOT EXISTS idx_rooms_floor_id ON bwi520.rooms(floor_id);
CREATE INDEX IF NOT EXISTS idx_rooms_code ON bwi520.rooms(code);

-- Test-Daten einfügen
INSERT INTO bwi520.rooms (floor_id, code, name) VALUES 
    (1, '1-101', 'Büro 101'),
    (1, '1-102', 'Meeting-Raum'),
    (1, '1-CR2', 'Konferenzraum'),
    (1, '1-F1', 'Flur'),
    (2, '2-201', 'Büro 201'),
    (2, '2-SR', 'Server-Raum'),
    (3, '3-301', 'Büro 301'),
    (3, '3-302', 'Büro 302')
ON CONFLICT (id) DO NOTHING;

-- Tabelle anzeigen
SELECT * FROM bwi520.rooms ORDER BY floor_id, code;
