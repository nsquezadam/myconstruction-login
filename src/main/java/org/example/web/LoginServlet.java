package org.example.web;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet(name="LoginServlet", urlPatterns={"/", "/login"})
public class LoginServlet extends HttpServlet {
    private final String url  = "jdbc:mysql://localhost:3309/myconstruction?useSSL=false&serverTimezone=UTC";
    private final String user = "mc_user";
    private final String pass = "mc_pass_123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("""
      <h2>Login MyConstruction</h2>
      <form method='post' action='/login'>
        <label>Usuario: <input name='username'></label><br>
        <label>Clave: <input type='password' name='password'></label><br>
        <button type='submit'>Ingresar</button>
      </form>
    """);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");
        boolean ok = false;

        try (Connection con = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = con.prepareStatement(
                     "SELECT 1 FROM usuarios WHERE username=? AND password_hash=?")) {
            ps.setString(1, u);
            ps.setString(2, p); // luego cambiamos a BCrypt
            try (ResultSet rs = ps.executeQuery()) { ok = rs.next(); }
        } catch (SQLException e) {
            resp.sendError(500, "Error DB: " + e.getMessage());
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        if (ok) {
            req.getSession(true).setAttribute("user", u);
            resp.getWriter().println("<h3>Bienvenido, " + u + ".</h3><a href='/'>Volver</a>");
        } else {
            resp.getWriter().println("<h3>Credenciales inválidas</h3><a href='/'>Intentar nuevamente</a>");
        }
    }
}
