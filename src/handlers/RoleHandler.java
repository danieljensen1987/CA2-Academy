package handlers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entities.Person;
import entities.RoleSchool;
import exceptions.NotFoundException;
import facades.PersonFacadeDB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoleHandler implements HttpHandler
{

    PersonFacadeDB facade;

    public RoleHandler() throws NotFoundException
    {
        facade = PersonFacadeDB.getFacade(false);
    }

    @Override
    public void handle(HttpExchange he) throws IOException
    {
        String response = "";
        int statusCode = 200;
        String method = he.getRequestMethod().toUpperCase();

        switch (method) {
            case "GET":
                break;

            case "POST":
                try {
                    InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String jsonQuery = br.readLine();

                    JsonElement jelement = new JsonParser().parse(jsonQuery);
                    JsonObject jobject = jelement.getAsJsonObject();
                    int id = Integer.parseInt(jobject.get("id").getAsString());
                    
                    RoleSchool role = facade.addRole(jsonQuery, id);
                    response = new Gson().toJson(role);
                    
                } catch (IllegalArgumentException iae) {
                    statusCode = 400;
                    response = iae.getMessage();
                } catch (IOException e) {
                    statusCode = 500;
                    response = "Internal Server Problem";
                } catch (NotFoundException nofe) {
                    response = nofe.getMessage();
                    statusCode = 404;
                }
                break;

            case "PUT":
                break;

            case "DELETE":
                break;
        }

        he.getResponseHeaders().add("Content-Type", "application/json");
        he.sendResponseHeaders(statusCode, 0);
        try (OutputStream os = he.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

}
