package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name="LoginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {


    private static final String URL  =
            "jdbc:mysql://127.0.0.1:3309/myconstruction?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "mc_user";
    private static final String PASS = "mc_pass_123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        final String u = req.getParameter("username");
        final String p = req.getParameter("password");

        boolean ok = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = con.prepareStatement(
                         "SELECT password_hash FROM usuarios WHERE username=?")) {

                ps.setString(1, u);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String hash = rs.getString(1);

                        // 🔎 Debug
                        System.out.println("Ingresado = " + p);
                        System.out.println("Hash en BD = " + hash);
                        System.out.println("Check = " + BCrypt.checkpw(p, hash));

                        ok = (hash != null && BCrypt.checkpw(p, hash));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error DB: " + e.getMessage());
            return;
        }


        final String ctx = req.getContextPath();
        if (ok) {
            req.getSession(true).setAttribute("user", u);
            resp.sendRedirect(ctx + "/home.jsp");
        } else {
            resp.sendRedirect(ctx + "/login.jsp?error=1");
        }
    }
}
