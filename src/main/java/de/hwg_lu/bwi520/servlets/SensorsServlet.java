package de.hwg_lu.bwi520.servlets;

import de.hwg_lu.bwi520.classes.SensorDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/sensors")
public class SensorsServlet extends HttpServlet {
  private final SensorDao dao = new SensorDao();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		    throws ServletException, IOException {
		  int roomId = Integer.parseInt(req.getParameter("roomId"));
		  try {
		    req.setAttribute("sensors", dao.byRoom(roomId));
		    req.setAttribute("roomId", roomId);

		    // Raumcode + Name aus DB lesen
		    try (var c  = de.hwg_lu.bwi520.jdbc.Db.get();
		         var ps = c.prepareStatement("SELECT code, name FROM room WHERE id=?")) {
		      ps.setInt(1, roomId);
		      try (var rs = ps.executeQuery()) {
		        if (rs.next()) {
		          String label = rs.getString("code") + " — " + rs.getString("name");
		          req.setAttribute("roomLabel", label);
		        }
		      }
		    }

		    req.getRequestDispatcher("/jsp/sensors.jsp").forward(req, resp);
		  } catch (SQLException e) {
		    throw new ServletException(e);
		  }
		}

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    int roomId   = Integer.parseInt(req.getParameter("roomId"));
    int sensorId = Integer.parseInt(req.getParameter("sensorId"));
    String floorId    = req.getParameter("floorId");
    String buildingId = req.getParameter("buildingId");

    // mehrere gleichnamige Felder (hidden + checkbox) -> letztes nehmen
    String[] vals = req.getParameterValues("desiredValue");
    String raw = (vals == null || vals.length == 0) ? null : vals[vals.length - 1];

    double desired = 0.0;
    try {
      if (raw != null) desired = Double.parseDouble(raw.replace(',', '.')); // 12,5 -> 12.5
    } catch (NumberFormatException ignore) { desired = 0.0; }

    try {
      if (dao.isWritable(sensorId)) {
        // 1) Mock-Schreiben (Sensorwert speichern)
        dao.insertValue(sensorId, desired);

        // 2) Protokollieren
        try (java.sql.Connection c = de.hwg_lu.bwi520.jdbc.Db.get();
             java.sql.PreparedStatement ps = c.prepareStatement(
                 "INSERT INTO control_request(sensor_id,requested_value) VALUES (?,?)")) {
          ps.setInt(1, sensorId);
          ps.setDouble(2, desired);
          ps.executeUpdate();
        }
      }

      // Redirect inkl. Kontext-Parameter für "zurück"
      String back = req.getContextPath() + "/sensors?roomId=" + roomId
                  + (floorId != null    ? "&floorId="    + floorId    : "")
                  + (buildingId != null ? "&buildingId=" + buildingId : "");
      resp.sendRedirect(back);

    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}
