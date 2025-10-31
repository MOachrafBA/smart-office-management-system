package de.hwg_lu.bwi520.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class AppDBAdminSmartOffice {

	Connection dbConn;
	
	public static void main(String[] args) throws SQLException {
		AppDBAdminSmartOffice myApp = new AppDBAdminSmartOffice();
		myApp.dbConn = new PostgreSQLAccess().getConnection();
		myApp.doSomething();
	}

	public void doSomething() throws SQLException {
		// Tabellen löschen (falls sie existieren)
		this.executeUpdateWithoutParms("DROP TABLE IF EXISTS control_request");
		this.executeUpdateWithoutParms("DROP TABLE IF EXISTS sensor_value");
		this.executeUpdateWithoutParms("DROP TABLE IF EXISTS sensor");
		this.executeUpdateWithoutParms("DROP TABLE IF EXISTS sensor_type");
		this.executeUpdateWithoutParms("DROP TABLE IF EXISTS room");
		this.executeUpdateWithoutParms("DROP TABLE IF EXISTS floor");
		this.executeUpdateWithoutParms("DROP TABLE IF EXISTS building");
		this.executeUpdateWithoutParms("DROP TABLE IF EXISTS room_type");
		this.executeUpdateWithoutParms("DROP TABLE IF EXISTS users");
		
		// Tabellen erstellen
		this.createTableUsers();
		this.createTableBuilding();
		this.createTableFloor();
		this.createTableRoomType();
		this.createTableRoom();
		this.createTableSensorType();
		this.createTableSensor();
		this.createTableSensorValue();
		this.createTableControlRequest();
		
		// Indizes erstellen
		this.createIndexes();
		
		// Testdaten einfügen
		this.insertDataUsers();
		this.insertDataBuilding();
		this.insertDataFloor();
		this.insertDataRoomType();
		this.insertDataRoom();
		this.insertDataSensorType();
		this.insertDataSensor();
		this.insertDataSensorValue();
	}
	
	public void executeUpdateWithoutParms(String sql) throws SQLException{
		System.out.println(sql);
		this.dbConn.prepareStatement(sql).executeUpdate();
	}
	
	public void createIndexes() throws SQLException {
		// Users Indizes
		this.executeUpdateWithoutParms("CREATE INDEX IF NOT EXISTS idx_users_active ON bwi520.users (is_active)");
		this.executeUpdateWithoutParms("CREATE INDEX IF NOT EXISTS idx_users_email ON bwi520.users (email)");
		this.executeUpdateWithoutParms("CREATE INDEX IF NOT EXISTS idx_users_username ON bwi520.users (username)");
		
		// Floor Indizes
		this.executeUpdateWithoutParms("CREATE INDEX IF NOT EXISTS idx_floor_building ON bwi520.floor (building_id, index_no)");
		
		// Room Indizes
		this.executeUpdateWithoutParms("CREATE INDEX IF NOT EXISTS idx_room_floor_type ON bwi520.room (floor_id, room_type_id)");
		
		// Sensor Indizes
		this.executeUpdateWithoutParms("CREATE INDEX IF NOT EXISTS idx_sensor_room ON bwi520.sensor (room_id)");
		
		// Sensor Value Indizes
		this.executeUpdateWithoutParms("CREATE INDEX IF NOT EXISTS idx_value_sensor_ts ON bwi520.sensor_value (sensor_id, ts DESC)");
		
		// Control Request Indizes
		this.executeUpdateWithoutParms("CREATE INDEX IF NOT EXISTS idx_control_request_username ON bwi520.control_request (username)");
	}
	
	public void createTableUsers() throws SQLException {
		this.executeUpdateWithoutParms(
			"CREATE TABLE bwi520.users(" + 
				"id SERIAL PRIMARY KEY," + 
				"username VARCHAR(50) UNIQUE NOT NULL," + 
				"email VARCHAR(100) UNIQUE NOT NULL," + 
				"password_hash VARCHAR(255) NOT NULL," + 
				"first_name VARCHAR(50)," + 
				"last_name VARCHAR(50)," + 
				"role VARCHAR(20) DEFAULT 'user'," + 
				"is_active BOOLEAN DEFAULT true," + 
				"created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP," + 
				"last_login TIMESTAMP WITHOUT TIME ZONE," + 
				"CONSTRAINT chk_role CHECK (role IN ('admin', 'user', 'manager'))" + 
			")"
		);		
	}
	
	public void createTableBuilding() throws SQLException {
		this.executeUpdateWithoutParms(
			"CREATE TABLE bwi520.building(" + 
				"id SERIAL PRIMARY KEY," + 
				"name TEXT NOT NULL" + 
			")"
		);		
	}
	
	public void createTableFloor() throws SQLException {
		this.executeUpdateWithoutParms(
			"CREATE TABLE bwi520.floor(" + 
				"id SERIAL PRIMARY KEY," + 
				"building_id INTEGER NOT NULL REFERENCES bwi520.building(id) ON DELETE CASCADE," + 
				"name TEXT NOT NULL," + 
				"index_no INTEGER NOT NULL" + 
			")"
		);		
	}
	
	public void createTableRoomType() throws SQLException {
		this.executeUpdateWithoutParms(
			"CREATE TABLE bwi520.room_type(" + 
				"id SERIAL PRIMARY KEY," + 
				"key TEXT UNIQUE NOT NULL," + 
				"label TEXT NOT NULL" + 
			")"
		);		
	}
	
	public void createTableRoom() throws SQLException {
		this.executeUpdateWithoutParms(
			"CREATE TABLE bwi520.room(" + 
				"id SERIAL PRIMARY KEY," + 
				"floor_id INTEGER NOT NULL REFERENCES bwi520.floor(id) ON DELETE CASCADE," + 
				"code TEXT NOT NULL," + 
				"name TEXT," + 
				"room_type_id INTEGER REFERENCES bwi520.room_type(id)" + 
			")"
		);		
	}
	
	public void createTableSensorType() throws SQLException {
		this.executeUpdateWithoutParms(
			"CREATE TABLE bwi520.sensor_type(" + 
				"id SERIAL PRIMARY KEY," + 
				"key TEXT UNIQUE NOT NULL," + //technische Bezeichnungen wie 'temperature', 'light'
				"unit TEXT," + 
				"writable BOOLEAN NOT NULL" + //ob der Sensor steuerbar ist.
			")"
		);		
	}
	
	public void createTableSensor() throws SQLException {
		this.executeUpdateWithoutParms(
			"CREATE TABLE bwi520.sensor(" + 
				"id SERIAL PRIMARY KEY," + 
				"room_id INTEGER NOT NULL REFERENCES bwi520.room(id) ON DELETE CASCADE," + 
				"type_id INTEGER NOT NULL REFERENCES bwi520.sensor_type(id)," + 
				"label TEXT NOT NULL," + 
				"addr TEXT" + 
			")"
		);		
	}
	
	public void createTableSensorValue() throws SQLException {
		this.executeUpdateWithoutParms(
			"CREATE TABLE bwi520.sensor_value(" + 
				"id SERIAL PRIMARY KEY," + 
				"sensor_id INTEGER NOT NULL REFERENCES bwi520.sensor(id) ON DELETE CASCADE," + 
				"value_numeric DOUBLE PRECISION," + 
				"value_text TEXT," + 
				"ts TIMESTAMP WITH TIME ZONE DEFAULT NOW()" + 
			")"
		);		
	}
	
	public void createTableControlRequest() throws SQLException {
		this.executeUpdateWithoutParms(
			"CREATE TABLE bwi520.control_request(" + 
				"id SERIAL PRIMARY KEY," + 
				"sensor_id INTEGER NOT NULL REFERENCES bwi520.sensor(id) ON DELETE CASCADE," + 
				"requested_value DOUBLE PRECISION NOT NULL," + 
				"created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()," + 
				"username VARCHAR(100)" + 
			")"
		);		
	}
	
	public void insertDataUsers() throws SQLException {
		this.executeUpdateWithoutParms(
			"INSERT INTO bwi520.users (username, email, password_hash, first_name, last_name, role) VALUES " +
			"('admin', 'admin@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K', 'Admin', 'User', 'admin'), " +
			"('manager', 'manager@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K8K', 'Manager', 'User', 'manager'), " +
			"('user', 'user@smartoffice.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQvOQj/3XgJ8K8K8K8K8K8K8K8K8K', 'Test', 'User', 'user')"
		);		
	}
	
	public void insertDataBuilding() throws SQLException {
		this.executeUpdateWithoutParms(
			"INSERT INTO bwi520.building (name) VALUES " +
			"('SmartOffice HQ')"
		);		
	}
	
	public void insertDataFloor() throws SQLException {
		this.executeUpdateWithoutParms(
			"INSERT INTO bwi520.floor (building_id, name, index_no) VALUES " +
			"(1, 'Erdgeschoss', 0), " +
			"(1, '1. Obergeschoss', 1), " +
			"(1, '2. Obergeschoss', 2)"
		);		
	}
	
	public void insertDataRoomType() throws SQLException {
		this.executeUpdateWithoutParms(
			"INSERT INTO bwi520.room_type (key, label) VALUES " +
			"('OFFICE', 'Büro'), " +
			"('MEETING', 'Besprechungsraum'), " +
			"('CORRIDOR', 'Flur'), " +
			"('STAIR', 'Treppenhaus'), " +
			"('KITCHEN', 'Küche'), " +
			"('WC', 'WC'), " +
			"('LOBBY', 'Lobby'), " +
			"('STORAGE', 'Lager'), " +
			"('SERVER', 'Serverraum')"
		);		
	}
	
	public void insertDataRoom() throws SQLException {
		this.executeUpdateWithoutParms(
			"INSERT INTO bwi520.room (floor_id, code, name, room_type_id) VALUES " +
			"(1, 'EG-LO', 'Lobby', 7), " +
			"(1, 'EG-CR1', 'Besprechung 1', 2), " +
			"(1, 'EG-KU', 'Küche', 5), " +
			"(1, 'EG-F1', 'Flur A', 3), " +
			"(1, 'EG-TR', 'Treppenhaus', 4), " +
			"(2, '1-101', 'Büro 101', 1), " +
			"(2, '1-102', 'Büro 102', 1), " +
			"(2, '1-CR2', 'Besprechung 2', 2), " +
			"(2, '1-F1', 'Flur B', 3), " +
			"(3, '2-201', 'Büro 201', 1), " +
			"(3, '2-SR', 'Serverraum', 9)"
		);		
	}
	
	public void insertDataSensorType() throws SQLException {
		this.executeUpdateWithoutParms(
			"INSERT INTO bwi520.sensor_type (key, unit, writable) VALUES " +
			"('temperature', '°C', false), " +
			"('temperature_setpoint', '°C', true), " +
			"('light', '%', true), " +
			"('blind_position', '%', true), " +
			"('motion', '', false), " +
			"('plug_switch', '', true), " +
			"('humidity', '%', false), " +
			"('co2', 'ppm', false)"
		);		
	}
	
	public void insertDataSensor() throws SQLException {
		this.executeUpdateWithoutParms(
			"INSERT INTO bwi520.sensor (room_id, type_id, label) VALUES " +
			// Lobby (Raum 1) - Empfangsbereich
			"(1, 3, 'Deckenlicht'), (1, 1, 'Raumtemp'), (1, 5, 'Bewegung'), " +
			// Besprechung 1 (Raum 2) 
			"(2, 1, 'Raumtemp'), (2, 2, 'Solltemp'), (2, 3, 'Licht'), (2, 4, 'Verschattung'), (2, 8, 'CO₂'), (2, 7, 'Feuchte'), (2, 5, 'Bewegung'), " +
			// Küche (Raum 3) - Gemeinschaftsküche
			"(3, 6, 'Steckdose Kaffeemaschine'), (3, 6, 'Kühlschrank'), (3, 6, 'Automat'), (3, 1, 'Raumtemp'), (3, 3, 'Licht'), (3, 5, 'Bewegung'), " +
			// Flur A (Raum 4)
			"(4, 5, 'Bewegung'), " +
			// Treppenhaus (Raum 5)
			"(5, 5, 'Bewegung'), " +
			// Büro 101 (Raum 6) - Einzelarbeitsplatz
			"(6, 1, 'Raumtemp'), (6, 2, 'Solltemp'), (6, 3, 'Licht'), (6, 4, 'Verschattung'), (6, 8, 'CO₂'), (6, 5, 'Bewegung'), " +
			// Büro 102 (Raum 7) - Einzelarbeitsplatz
			"(7, 1, 'Raumtemp'), (7, 2, 'Solltemp'), (7, 3, 'Licht'), (7, 4, 'Verschattung'), (7, 8, 'CO₂'), (7, 5, 'Bewegung'), " +
			// Besprechung 2 (Raum 8)
			"(8, 1, 'Raumtemp'), (8, 2, 'Solltemp'), (8, 3, 'Licht'), (8, 4, 'Verschattung'), (8, 8, 'CO₂'), (8, 7, 'Feuchte'), (8, 5, 'Bewegung'), " +
			// Flur B (Raum 9)
			"(9, 5, 'Bewegung'), " +
			// Büro 201 (Raum 10) - Einzelarbeitsplatz
			"(10, 1, 'Raumtemp'), (10, 2, 'Solltemp'), (10, 3, 'Licht'), (10, 4, 'Verschattung'), (10, 8, 'CO₂'), (10, 5, 'Bewegung'), " +
			// Serverraum (Raum 11) - IT-Infrastruktur
			"(11, 1, 'Lufttemp'), (11, 7, 'Luftfeuchte'), (11, 3, 'Deckenlicht'), (11, 5, 'Bewegung')"
		);		
	}
	
	public void insertDataSensorValue() throws SQLException {
		this.executeUpdateWithoutParms(
			"INSERT INTO bwi520.sensor_value (sensor_id, value_numeric, ts) VALUES " +
			// Lobby (Raum 1) - Deckenlicht, Raumtemp, Bewegung
			"(1, 100.0, NOW()), (2, 22.0, NOW()), (3, 0.0, NOW()), " +
			// Besprechung 1 (Raum 2) - Raumtemp, Solltemp, Licht, Verschattung, CO₂, Feuchte, Bewegung
			"(4, 22.0, NOW()), (5, 21.0, NOW()), (6, 80.0, NOW()), (7, 0.0, NOW()), (8, 450.0, NOW()), (9, 55.0, NOW()), (10, 0.0, NOW()), " +
			// Küche (Raum 3) - Steckdosen, Raumtemp, Licht, Bewegung
			"(11, 1.0, NOW()), (12, 0.0, NOW()), (13, 1.0, NOW()), (14, 23.0, NOW()), (15, 90.0, NOW()), (16, 0.0, NOW()), " +
			// Flur A (Raum 4) - Bewegung
			"(17, 0.0, NOW()), " +
			// Treppenhaus (Raum 5) - Bewegung
			"(18, 0.0, NOW()), " +
			// Büro 101 (Raum 6) - Raumtemp, Solltemp, Licht, Verschattung, CO₂, Bewegung
			"(19, 22.5, NOW()), (20, 21.0, NOW()), (21, 75.0, NOW()), (22, 0.0, NOW()), (23, 420.0, NOW()), (24, 0.0, NOW()), " +
			// Büro 102 (Raum 7) - Raumtemp, Solltemp, Licht, Verschattung, CO₂, Bewegung
			"(25, 23.0, NOW()), (26, 21.5, NOW()), (27, 80.0, NOW()), (28, 25.0, NOW()), (29, 380.0, NOW()), (30, 0.0, NOW()), " +
			// Besprechung 2 (Raum 8) - Raumtemp, Solltemp, Licht, Verschattung, CO₂, Feuchte, Bewegung
			"(31, 23.0, NOW()), (32, 22.0, NOW()), (33, 70.0, NOW()), (34, 30.0, NOW()), (35, 380.0, NOW()), (36, 50.0, NOW()), (37, 0.0, NOW()), " +
			// Flur B (Raum 9) - Bewegung
			"(38, 0.0, NOW()), " +
			// Büro 201 (Raum 10) - Raumtemp, Solltemp, Licht, Verschattung, CO₂, Bewegung
			"(39, 24.0, NOW()), (40, 22.0, NOW()), (41, 90.0, NOW()), (42, 75.0, NOW()), (43, 400.0, NOW()), (44, 0.0, NOW()), " +
			// Serverraum (Raum 11) - Lufttemp, Luftfeuchte, Deckenlicht, Bewegung
			"(45, 18.0, NOW()), (46, 45.0, NOW()), (47, 100.0, NOW()), (48, 0.0, NOW())"
		);		
	}
}
