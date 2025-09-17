package de.hwg_lu.bwi520.servlets;

import de.hwg_lu.bwi520.classes.FloorDao;
import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*; import java.io.*; import java.sql.*;

@WebServlet("/building")
public class BuildingViewServlet extends HttpServlet {
  private final FloorDao floorDao = new FloorDao();

  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    int buildingId = Integer.parseInt(req.getParameter("buildingId"));
    try {
      req.setAttribute("buildingId", buildingId);
      req.setAttribute("floors", floorDao.byBuilding(buildingId)); // EG..2.OG sortiert
      req.getRequestDispatcher("/jsp/building.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }
}
