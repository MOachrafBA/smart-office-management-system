package de.hwg_lu.bwi520.servlets;

import de.hwg_lu.bwi520.classes.BuildingDao;
import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*; import java.io.*; import java.sql.*;

@WebServlet("/buildings")
public class BuildingsServlet extends HttpServlet {
  private final BuildingDao dao = new BuildingDao();
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      // 1. Daten aus Datenbank holen(DAO holt Gebäude aus Datenbank)
      req.setAttribute("buildings", dao.findAll());
      // 2. Attribute an JSP-Seite übergeben
      req.getRequestDispatcher("/jsp/buildings.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }
}
