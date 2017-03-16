package hazelserver.com.styopik.main;

import java.io.IOException;

import hazelserver.com.styopik.server.Server;

public class App {

    public static final int PORT = 8000;

    public static void main( String[] args ) throws IOException {

        Server server = new Server();

        server.start();
    }
}
