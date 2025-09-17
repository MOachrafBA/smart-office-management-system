package de.hwg_lu.bwi520.servlets;
import de.hwg_lu.bwi520.classes.FloorDao;
import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*; import java.io.*; import java.sql.*;
@WebServlet("/floors")
public class FloorsServlet extends HttpServlet {
  private final FloorDao dao = new FloorDao();
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    int buildingId = Integer.parseInt(req.getParameter("buildingId"));
    try {
      req.setAttribute("floors", dao.byBuilding(buildingId));
      req.getRequestDispatcher("/jsp/floors.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }
}
