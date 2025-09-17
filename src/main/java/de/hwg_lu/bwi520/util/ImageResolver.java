package de.hwg_lu.bwi520.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;

public class ImageResolver {
  public static String roomPhoto(HttpServletRequest req, String roomCode, String typeKey) {
    ServletContext sc = req.getServletContext();
    String ctx  = req.getContextPath();
    String code = roomCode == null ? "" : roomCode.trim();
    String lc   = code.toLowerCase(Locale.ROOT);

    // 1) Raum-spezifische Kandidaten (Original + lower-case)
    String[] room = {
      "/img/rooms/room-" + code + ".jpg",
      "/img/rooms/room-" + code + ".png",
      "/img/rooms/room-" + lc   + ".jpg",
      "/img/rooms/room-" + lc   + ".png"
    };
    for (String p : room) {
      try { if (sc.getResource(p) != null) return ctx + p; } catch (Exception ignore) {}
    }

    // 2) Typ-Fallback
    String t = (typeKey == null || typeKey.isBlank())
             ? guessType(lc)
             : typeKey.toLowerCase(Locale.ROOT);
    String[] typ = {
      "/img/rooms/type-" + t + ".jpg",
      "/img/rooms/type-" + t + ".png"
    };
    for (String p : typ) {
      try { if (sc.getResource(p) != null) return ctx + p; } catch (Exception ignore) {}
    }

    // 3) Default (lege bei Bedarf ein neutrales Bild ab)
    return ctx + "/img/rooms/room-default.jpg";
  }

  private static String guessType(String lc){
    if (lc.contains("-sr")) return "server";
    if (lc.contains("-cr")) return "meeting";
    if (lc.matches(".*-f\\d+")) return "corridor";
    if (lc.contains("ku")) return "kitchen";  // EG-KU
    if (lc.contains("lo")) return "lobby";    // EG-LO
    return "office";
  }
}
