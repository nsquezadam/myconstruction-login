package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.example.web.LoginServlet;

public class App {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8090); // 8080 jenkins  , 8082 jfrog

        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ctx.setContextPath("/");
        ctx.addServlet(LoginServlet.class, "/");       // formulario en GET
        ctx.addServlet(LoginServlet.class, "/login");  // procesa POST

        server.setHandler(ctx);
        server.start();
        System.out.println("Jetty arriba: http://localhost:8090/");
        server.join();
    }
}
