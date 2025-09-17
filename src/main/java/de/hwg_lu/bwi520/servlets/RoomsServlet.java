package de.hwg_lu.bwi520.servlets;
import de.hwg_lu.bwi520.classes.RoomDao;
import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*; import java.io.*; import java.sql.*;
@WebServlet("/rooms")
public class RoomsServlet extends HttpServlet {
  private final RoomDao dao = new RoomDao();
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    int floorId = Integer.parseInt(req.getParameter("floorId"));
    try {
      req.setAttribute("rooms", dao.byFloor(floorId));
      req.getRequestDispatcher("/jsp/rooms.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }
}
