package hazelserver.com.styopik.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import hazelserver.com.styopik.model.User;
import hazelserver.com.styopik.service.UserService;
import hazelserver.com.styopik.service.impl.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class);

    private UserService userService = new UserServiceImpl();
    private HttpServer server;

    private static final int PORT = 8000;

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/userinfo", (t) -> {
            String method = t.getRequestMethod();

            if ("GET".equals(method)) {
                try {
                    processHandle(t, 0);
                } catch (Exception e) {
                    LOGGER.error("Error while fetching users by ID!");
                }
            }
        });
        
        server.createContext("/levelinfo", (t) -> {
            String method = t.getRequestMethod();

            if ("GET".equals(method)) {
                try {
                    processHandle(t, 1);
                } catch (Exception e) {
                    LOGGER.error("Error while fetching users by LEVEL!");
                }
            }
        });
        
        server.createContext("/setinfo", new SetHandler());
        server.start();
        
        userService.putUser(new User(1,1,1));
        userService.putUser(new User(2,1,1));
        userService.putUser(new User(3,1,1));
        userService.putUser(new User(4,1,1));
        userService.putUser(new User(5,1,1));
        userService.putUser(new User(6,1,1));
        userService.putUser(new User(7,1,1));

        LOGGER.info("Server has been started on port " + PORT);
    }
    
    private void processHandle(HttpExchange t, int flag) throws IOException {
        String path = t.getRequestURI().toString();
        int id = Integer.parseInt(path.split("/")[path.split("/").length - 1]);
        List<User> usersList = (flag == 1) ? userService.getUserResultsByLevel(id) : userService.getUsersById(id);
        String response = new Gson().toJson(usersList);

        t.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
        t.getResponseBody().write(response.getBytes());
        t.close();

        LOGGER.info("Fetching users by " + ((flag == 1) ? "LEVEL = " : "ID = ") + id);
    }

    private class SetHandler implements HttpHandler {

        public void handle(HttpExchange t) throws IOException {
            String method = t.getRequestMethod();

            if ("PUT".equals(method)) {
                User newUser = null;
                JSONObject requestJson;

                BufferedReader rd = new BufferedReader(new InputStreamReader(t.getRequestBody()));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();

                requestJson = new JSONObject(sb.toString());

                try {
                    newUser = new User(requestJson.getInt("id"), requestJson.getInt("level"), requestJson.getDouble("result"));
                } catch (JSONException ex) {
                    LOGGER.error("Error while fetching User from request Json!");
                }

                userService.putUser(newUser);

                String response = "Adding is OK";

                t.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
                t.getResponseBody().write(response.getBytes());
                t.close();

                LOGGER.info("Adding " + newUser);
            }
        }
    }
}
