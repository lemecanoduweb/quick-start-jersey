package com.lemecanoduweb.quickstartjersey;

import com.lemecanoduweb.quickstartjersey.bootstrap.AppResourceConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class Main {

    public static Server createServer(ResourceConfig resourceConfig, Integer port) {
        var context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        var container = new ServletContainer(resourceConfig);
        context.addServlet(new ServletHolder(container), "/*");

        var jettyServer = new Server(port);
        jettyServer.setHandler(context);
        return jettyServer;
    }

    public static void startServer(Server server) {
        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            server.destroy();
        }
    }

    public static void main(String[] args) {
        var jettyServer = createServer(new AppResourceConfig(), 8080);

        startServer(jettyServer);
    }
}
