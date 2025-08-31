package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.example.web.LoginServlet;

public class App {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8090); // 8080 jenkins /8082  jfrog

        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ctx.setContextPath("/");

        // Servir src/main/webapp/ estaticos
        ctx.setResourceBase(App.class.getResource("/webapp").toExternalForm());
        ctx.addServlet(DefaultServlet.class, "/");

        // Servlet de login /  POST
        ctx.addServlet(LoginServlet.class, "/login");

        server.setHandler(ctx);
        server.start();
        System.out.println("App en http://localhost:8090/login.html");
        server.join();
    }
}
