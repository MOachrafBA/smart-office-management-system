-- Floors-Tabelle für Smart Office System
-- Erstellt eine Tabelle für Etagen im bwi520 Schema

-- Schema festlegen
SET search_path TO bwi520;

-- Floors-Tabelle erstellen
CREATE TABLE IF NOT EXISTS bwi520.floors (
    id integer NOT NULL DEFAULT nextval('bwi520.floors_id_seq'::regclass),
    building_id integer NOT NULL,
    name VARCHAR(50) NOT NULL,
    index_no integer NOT NULL,
    CONSTRAINT floors_pkey PRIMARY KEY (id),
    CONSTRAINT fk_floors_building FOREIGN KEY (building_id) REFERENCES bwi520.building(id)
);

-- Sequence für ID erstellen
CREATE SEQUENCE IF NOT EXISTS bwi520.floors_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

-- Owner setzen
ALTER TABLE IF EXISTS bwi520.floors OWNER TO bwi520;
ALTER SEQUENCE IF EXISTS bwi520.floors_id_seq OWNER TO bwi520;

-- Berechtigungen setzen
GRANT ALL ON TABLE bwi520.floors TO bwi520;
GRANT ALL ON SEQUENCE bwi520.floors_id_seq TO bwi520;

-- Index für schnelle Etagensuche
CREATE INDEX IF NOT EXISTS idx_floors_building_id ON bwi520.floors(building_id);
CREATE INDEX IF NOT EXISTS idx_floors_index_no ON bwi520.floors(index_no);

-- Test-Daten einfügen
INSERT INTO bwi520.floors (building_id, name, index_no) VALUES 
    (1, 'Erdgeschoss', 0),
    (1, '1. Obergeschoss', 1),
    (1, '2. Obergeschoss', 2),
    (2, 'Erdgeschoss', 0),
    (2, '1. Obergeschoss', 1)
ON CONFLICT (id) DO NOTHING;

-- Tabelle anzeigen
SELECT * FROM bwi520.floors ORDER BY building_id, index_no;
