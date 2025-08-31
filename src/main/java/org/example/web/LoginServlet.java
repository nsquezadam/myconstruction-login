package org.example.web;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet(name="LoginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
    private final String url  =
            "jdbc:mysql://127.0.0.1:3309/myconstruction?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String user = "mc_user";
    private final String pass = "mc_pass_123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // siempre mostrar el formulario estático
        resp.sendRedirect("/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        boolean ok = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(url, user, pass);
                 PreparedStatement ps = con.prepareStatement(
                         "SELECT 1 FROM usuarios WHERE username=? AND password_hash=?")) {
                ps.setString(1, u);
                ps.setString(2, p);
                try (ResultSet rs = ps.executeQuery()) { ok = rs.next(); }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, "Error DB: " + e.getMessage());
            return;
        }

        if (ok) {
            req.getSession(true).setAttribute("user", u);
            resp.sendRedirect("/home.html");
        } else {
            // volver al login con un query param “error”
            resp.sendRedirect("/login.html?error=1");
        }
    }
}
