package com.ddk.karthi.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class App {
    public App() throws Exception {
        this.port = Integer.parseInt(System.getProperty("port", "2222"));
        startServer();
    }

    private void startServer() throws Exception {
        ResourceConfig config = new ResourceConfig();
        config.packages("com.ddk.karthi");
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(this.port);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/*");

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    private Integer port;
}
