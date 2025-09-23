Smart Office Management System – Technische Dokumentation

1. Einleitung und Use Cases

1.1 Projektübersicht
Das Smart Office Management System ist eine Java-basierte Webanwendung zur Verwaltung und Überwachung von intelligenten Bürogebäuden. Die Anwendung ermöglicht eine hierarchische Navigation von Gebäuden über Etagen zu Räumen und zeigt dort installierte Sensoren an bzw. erlaubt deren Steuerung.

1.2 Use Cases

UC-1: Gebäudeübersicht anzeigen

* Akteur: Benutzer
* Beschreibung: Der Benutzer kann eine Liste aller verfügbaren Gebäude einsehen.
* Vorbedingung: Anwendung ist gestartet.
* Nachbedingung: Liste der Gebäude wird angezeigt.

UC-2: Etagen eines Gebäudes anzeigen

* Akteur: Benutzer
* Beschreibung: Der Benutzer wählt ein Gebäude aus und sieht alle verfügbaren Etagen.
* Vorbedingung: Gebäudeübersicht ist sichtbar.
* Nachbedingung: Etagenliste wird angezeigt.

UC-3: Räume einer Etage anzeigen

* Akteur: Benutzer
* Beschreibung: Der Benutzer wählt eine Etage aus und sieht alle Räume mit Bildern.
* Vorbedingung: Etagenliste ist sichtbar.
* Nachbedingung: Raumübersicht mit Bildern wird angezeigt.

UC-4: Sensoren eines Raumes überwachen

* Akteur: Benutzer
* Beschreibung: Der Benutzer wählt einen Raum aus und sieht alle Sensoren mit aktuellen Werten.
* Vorbedingung: Raumübersicht ist sichtbar.
* Nachbedingung: Sensortabelle wird angezeigt.

UC-5: Sensoren steuern

* Akteur: Benutzer
* Beschreibung: Der Benutzer kann steuerbare Sensoren (Licht, Temperatur, Steckdosen) bedienen.
* Vorbedingung: Sensortabelle ist sichtbar.
* Nachbedingung: Sensorwerte werden aktualisiert.

2. Datenbankstruktur und Datenmodell

2.1 Datenbankschema
Die Anwendung verwendet eine PostgreSQL-Datenbank mit folgender Struktur:

```sql
-- Gebäude
CREATE TABLE building (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Etagen
CREATE TABLE floor (
    id SERIAL PRIMARY KEY,
    building_id INTEGER REFERENCES building(id),
    name VARCHAR(50) NOT NULL,
    index_no INTEGER NOT NULL
);

-- Räume
CREATE TABLE room (
    id SERIAL PRIMARY KEY,
    floor_id INTEGER REFERENCES floor(id),
    code VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL
);

-- Sensortypen
CREATE TABLE sensor_type (
    id SERIAL PRIMARY KEY,
    key VARCHAR(50) NOT NULL,
    unit VARCHAR(20),
    writable BOOLEAN DEFAULT FALSE
);

-- Sensoren
CREATE TABLE sensor (
    id SERIAL PRIMARY KEY,
    room_id INTEGER REFERENCES room(id),
    type_id INTEGER REFERENCES sensor_type(id),
    label VARCHAR(100) NOT NULL
);

-- Sensorwerte
CREATE TABLE sensor_value (
    id SERIAL PRIMARY KEY,
    sensor_id INTEGER REFERENCES sensor(id),
    value_numeric DECIMAL(10,2),
    value_text TEXT,
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Steueranfragen
CREATE TABLE control_request (
    id SERIAL PRIMARY KEY,
    sensor_id INTEGER REFERENCES sensor(id),
    requested_value DECIMAL(10,2),
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

2.2 Datenmodell (ER-Diagramm, Textform)

Building (1) —< (N) Floor (1) —< (N) Room (1) —< (N) Sensor
SensorType (1) —< (N) Sensor
Sensor (1) —< (N) SensorValue
Sensor (1) —< (N) ControlRequest

2.3 Beispieldaten

* Gebäude: „Hauptgebäude“, „Nebengebäude“
* Etagen: „Erdgeschoss“, „1. Obergeschoss“, „2. Obergeschoss“
* Räume: „1-101 (Büro)“, „1-102 (Meeting)“, „EG-KU (Küche)“
* Sensortypen: „light“, „temperature“, „blind\_position“, „plug\_switch“

3. Klassen und JSPs sowie verwendete Techniken

3.1 Architektur-Pattern: MVC

#### Moderne MVC-Implementierung (Aktuelle Anwendung)

**Model (Beans)**
* Building: POJO für Gebäude
* Floor: POJO für Etagen (mit indexNo für Sortierung)
* Room: POJO für Räume (code, name)
* SensorRow: POJO für Sensordaten inkl. aktuellem Wert

#### Alternative: Dozent's Kochrezept (Traditionell)

**Session-basierte Beans:**
```java
public class BuildingsBean {
  private List<Building> buildings;
  private String message;
  
  public void loadBuildings() {
    try {
      this.buildings = buildingDao.findAll();
      this.message = "Gebäude erfolgreich geladen";
    } catch (SQLException e) {
      this.message = "Fehler beim Laden der Gebäude";
    }
  }
}
```

**Appl als Controller:**
```java
public class BuildingsAppl extends HttpServlet {
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    // 1. Beans in Session einbinden
    BuildingsBean bean = (BuildingsBean) session.getAttribute("buildingsBean");
    if (bean == null) {
      bean = new BuildingsBean();
      session.setAttribute("buildingsBean", bean);
    }
    
    // 2. Parameter übernehmen
    String action = req.getParameter("action");
    
    // 3. Aktionsweiche
    if ("showBuildings".equals(action)) {
      bean.loadBuildings();
      req.setAttribute("message", bean.getMessage());
    } else {
      bean.loadBuildings();
      req.setAttribute("message", "Willkommen");
    }
    
    // 4. Weiterleitung
    req.getRequestDispatcher("/jsp/buildings.jsp").forward(req, resp);
  }
}
```

**Vorteile beider Ansätze:**
- **Moderne MVC**: Saubere Trennung, RESTful URLs, Wartbarkeit
- **Dozent's Kochrezept**: Session-basiert, Aktionsweichen, Messages

View (JSPs)

* buildings.jsp: Gebäudeübersicht mit Navigation
* building.jsp: Etagenansicht (auch mit Hotspots möglich)
* rooms.jsp: Raumübersicht mit Bildern (ImageResolver)
* sensors.jsp: Sensortabelle mit Steuerung und Auto-Refresh

Controller (Servlets)

* BuildingsServlet: Controller für Gebäude
* BuildingViewServlet: Controller für Etagen
* FloorsServlet: Alternative Route für Etagen
* RoomsServlet: Controller für Räume
* SensorsServlet: Controller für Sensoren (GET/POST)

3.2 Data Access Layer

DAOs (Data Access Objects)

* BuildingDao: Datenzugriff für Gebäude
* FloorDao: Datenzugriff für Etagen
* RoomDao: Datenzugriff für Räume
* SensorDao: Datenzugriff für Sensoren

JDBC-Layer

* Db: Singleton/Hilfsklasse für DB-Verbindung
* PostgreSQLAccess: PostgreSQL-Konfiguration
* NoConnectionException: eigene Exception für DB-Fehler

3.3 Utility-Klassen

ImageResolver
Signatur: roomPhoto(HttpServletRequest req, String roomCode, String typeKey)
Funktion: Ermittelt das passende Bild für einen Raum.
Strategie:

1. Raum-spezifische Bilder: /img/rooms/room-{code}.jpg
2. Typ-Fallbacks: /img/rooms/type-{type}.jpg
3. Default-Bild: /img/rooms/room-default.jpg
   Typ-Erkennung: Ableitung aus dem Raumcode (z. B. SR → server, CR → meeting)

3.4 Frontend-Technologien

CSS

* app.css: Grundlegende Styles und Layout
* building.css: Styles für Etagen-Hotspots
* plan.css: Grid-Layout für Raumkarten

JavaScript

* Auto-Refresh: setInterval(() => location.reload(), 10000) auf der Sensorseite
* Form-Handling: dynamische Eingaben je Sensortyp

HTML5-Inputs

* Range-Slider: Licht/Jalousie (0–100 %)
* Number-Input: Temperatur (mit Dezimalstellen)
* Checkbox: Steckdosen (an/aus)

3.5 Besondere Techniken

1. ImageResolver-Strategie (vereinfacht, Idee)

* Versuch raum-spezifischer Bildpfade
* Falls nicht vorhanden, typbasierter Fallback
* Sonst Default-Bild

2. Hotspot-Navigation (Beispielidee)

* Klickbare Bereiche verlinken auf /rooms?floorId=…

3. Sensor-typ-spezifische Steuerung (JSP-Logik)

* Range für light/blind\_position
* Number für temperature\_setpoint
* Checkbox für plug\_switch

4. Installations- und Konfigurationsanleitung

4.1 Systemanforderungen

* Java: JDK 11 oder höher
* Eclipse: 2023-03 oder höher
* Tomcat: 10.0 oder höher
* PostgreSQL: 13 oder höher

4.2 Datenbank-Setup

1. PostgreSQL installieren und starten

* Windows: Installer von der PostgreSQL-Website
* Linux/macOS: Paketmanager verwenden, Dienst starten

2. Datenbank und Schema erstellen (Beispiel)

```sql
CREATE DATABASE bwi520;
CREATE USER bwi520 WITH PASSWORD 'bwi520?!';
GRANT ALL PRIVILEGES ON DATABASE bwi520 TO bwi520;

-- danach im DB-Client zur DB verbinden und Schema/Tabellen anlegen
```

3. Beispieldaten einfügen (Auszug)

```sql
INSERT INTO building (name) VALUES ('Hauptgebäude'), ('Nebengebäude');

INSERT INTO floor (building_id, name, index_no) VALUES
(1, 'Erdgeschoss', 0), (1, '1. Obergeschoss', 1), (1, '2. Obergeschoss', 2);

INSERT INTO room (floor_id, code, name) VALUES
(1, 'EG-KU', 'Küche'), (1, 'EG-LO', 'Lobby'),
(2, '1-101', 'Büro 101'), (2, '1-102', 'Büro 102');

INSERT INTO sensor_type (key, unit, writable) VALUES
('light', '%', true), ('temperature', '°C', false),
('blind_position', '%', true), ('plug_switch', '', true);
```

4.3 Eclipse-Projekt-Setup

1. Projekt importieren

* File → Import → Existing Projects into Workspace → Projektordner wählen

2. Tomcat konfigurieren

* Window → Show View → Servers → New → Apache Tomcat v10
* Tomcat-Installation auswählen, Projekt hinzufügen

3. Datenbankverbindung konfigurieren

* Datei src/main/resources/db.properties

```
db.url=jdbc:postgresql://localhost:5432/postgres
db.user=bwi520
db.password=bwi520?!
db.schema=bwi520
```

4. PostgreSQL JDBC-Treiber

* JAR in WEB-INF/lib legen oder per Build-Tool einbinden

4.4 Projekt starten

1. Datenbankdienst starten (plattformabhängig)
2. Projekt auf Tomcat deployen und Server starten
3. Browser: [http://localhost:8080/BWI520](http://localhost:8080/BWI520)

4.5 Troubleshooting (Kurz)

* Datenbankverbindung: db.properties prüfen, Dienst läuft?
* Jakarta-Imports: Tomcat 10+ erforderlich
* Bilder: liegen unter src/main/webapp/img/…
* CSS: liegt unter src/main/webapp/css/…

5. Abgrenzungserklärung

5.1 Projektentwicklung
Entwickler: Mohamed Achraf Badaoui, Harun
Projekt: Smart Office Management System

5.2 Entwickelte Komponenten

* Servlets (Controller): BuildingsServlet, BuildingViewServlet, FloorsServlet, RoomsServlet, SensorsServlet
* JSPs (Views): buildings.jsp, building.jsp, rooms.jsp, sensors.jsp
* DAOs: BuildingDao, FloorDao, RoomDao, SensorDao
* Beans/POJOs: Building, Floor, Room, SensorRow
* JDBC-Layer: Db, PostgreSQLAccess
* Utilities: ImageResolver
* Frontend: CSS/JS
* Datenbank: Schema-Design und Beispieldaten

Externe Komponenten

* PostgreSQL, Apache Tomcat, Jakarta EE API, PostgreSQL JDBC Driver

5.3 Besondere Leistungen

* Intelligente Bildauflösung (ImageResolver)
* Interaktive Gebäude-Navigation
* Sensor-typ-spezifische Steuerung
* Klare MVC-Struktur

5.4 Bestätigung
Wir bestätigen, dass die genannten Komponenten von uns entwickelt wurden und die Anwendung die Anforderungen der Lehrveranstaltung „Webanwendungen” erfüllt.

6. Fazit

Das Smart Office Management System zeigt eine moderne, benutzerfreundliche JSP-Anwendung mit klarer Architektur, guter Wartbarkeit und Erweiterbarkeit. Die App erfüllt die Pflichtanforderungen (JSP/Beans, DB-Zugriff, mindestens vier Tabellen, Präsentation) und ergänzt sinnvolle Komfortfunktionen wie Bild-Fallbacks und typabhängige Steuerung.
