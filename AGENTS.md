# AGENTS.md

## Cursor Cloud specific instructions

### Project overview

Smart Office Management System (BWI520) — a Java JSP web application for building/sensor monitoring and control. No build tool (Maven/Gradle); uses `javac` directly. See `README.md` for full feature description.

### Required services

| Service | Details |
|---------|---------|
| **PostgreSQL 16** | `localhost:5432`, database `postgres`, schema `bwi520`, user `postgres`, password `pgusers` |
| **Apache Tomcat 10.1.13** | Installed at `/opt/tomcat`, app deployed at `/BWI520` |
| **JDK 24** | Installed at `/opt/jdk24` (required — pre-compiled `.class` files use Java 24 bytecode version 68.0) |

### Starting services

```bash
# Start PostgreSQL
sudo pg_ctlcluster 16 main start

# Start Tomcat (must set JAVA_HOME to JDK 24)
export JAVA_HOME=/opt/jdk24
export CATALINA_HOME=/opt/tomcat
$CATALINA_HOME/bin/startup.sh
```

App URL: `http://localhost:8080/BWI520/jsp/LoginView.jsp`

### Compiling Java sources

```bash
export JAVA_HOME=/opt/jdk24
export PATH=$JAVA_HOME/bin:$PATH
javac -cp "/opt/tomcat/lib/servlet-api.jar:/opt/tomcat/lib/jsp-api.jar:/tmp/postgresql-42.7.4.jar" \
  -d /workspace/build/classes \
  -sourcepath /workspace/src/main/java \
  $(find /workspace/src/main/java -name "*.java")
```

After compiling, redeploy classes to Tomcat:
```bash
sudo cp -r /workspace/build/classes/* /opt/tomcat/webapps/BWI520/WEB-INF/classes/
```

Tomcat auto-reloads JSP changes, but `.class` file changes require restarting Tomcat.

### Database initialization

To reset the database with fresh seed data:
```bash
export JAVA_HOME=/opt/jdk24
export PATH=$JAVA_HOME/bin:$PATH
java -cp "/workspace/build/classes:/tmp/postgresql-42.7.4.jar" de.hwg_lu.bwi520.jdbc.AppDBAdminSmartOffice
```

### Test credentials (hardcoded in `UserDao.java`)

| Username | Password | Role |
|----------|----------|------|
| `admin` | `admin123` | admin |
| `manager` | `manager123` | manager |
| `user` | `user123` | user |

### Non-obvious gotchas

- The JDBC driver JAR must be at `/tmp/postgresql-42.7.4.jar` for compilation and also in `/opt/tomcat/webapps/BWI520/WEB-INF/lib/` for runtime.
- Tomcat work directory (`/opt/tomcat/work/`) must be writable; if JSP compilation fails with "No output directory", run `sudo chmod -R a+rwX /opt/tomcat/work`.
- There is no `web.xml`; all JSP pages are accessed directly via their path (e.g., `/BWI520/jsp/LoginView.jsp`).
- There are no automated tests in this project (no test framework, no test directory).
- There is no lint tool configured for this project.
