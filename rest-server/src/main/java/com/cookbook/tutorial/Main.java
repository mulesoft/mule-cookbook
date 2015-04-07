package com.cookbook.tutorial;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import com.sun.net.httpserver.HttpHandler;

/**
 * Created by Mulesoft.
 */
public class Main {
    /**
     * Starts the lightweight HTTP server serving the JAX-RS application.
     *
     * @return new instance of the lightweight HTTP server
     * @throws IOException
     */
    static HttpServer startServer() throws IOException {
        // create a new server listening at port 9091
        HttpServer server = HttpServer.create(new InetSocketAddress(getBaseURI().getPort()), 0);
        // create a handler wrapping the JAX-RS application
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new JaxRsApplication(), HttpHandler.class);
        // map JAX-RS handler to the server root
        server.createContext(getBaseURI().getPath(), handler);
        // start the server
        server.start();
        return server;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("\"Hello World\" Jersey Example Application");
        HttpServer server = startServer();
        System.out.println("Application started.\n" +
                "Try accessing " + getBaseURI() + "ingredient in the browser.\n" +
                "Hit enter to stop the application...");
        System.in.read();
        server.stop(0);
        System.out.println("Server terminated");
    }
    private static int getPort(int defaultPort) {
        final String port = System.getProperty("jersey.config.test.container.port");
        if (null != port) {
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException e) {
                System.out.println("Value of jersey.config.test.container.port property" +
                        " is not a valid positive integer [" + port + "]." +
                        " Reverting to default [" + defaultPort + "].");
            }
        }
        return defaultPort;
    }
    /**
     * Gets base {@link URI}.
     *
     * @return base {@link URI}.
     */
    public static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(getPort(9091)).build();
    }
}

