package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.NotFoundException;
import facades.PersonFacade;
import facades.PersonFacadeDB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import entities.Person;

public class PersonHandler implements HttpHandler
{

//    PersonFacade facade;
    PersonFacadeDB facade;
    private static final boolean devolpment = true;

    public PersonHandler()
    {
        facade = PersonFacadeDB.getFacade(false);
        if (devolpment) {
            facade.createTestData();
        }
    }

    @Override
    public void handle(HttpExchange he) throws IOException
    {
        String response = "";
        int statusCode = 200;
        String method = he.getRequestMethod().toUpperCase();

        switch (method) {
            case "GET":
                try {
                    String path = he.getRequestURI().getPath();
                    int lastIndexOf = path.lastIndexOf("/");

                    if (lastIndexOf > 0) {
                        String idString = path.substring(lastIndexOf + 1);
                        int id = Integer.parseInt(idString);
                        response = facade.getPerson(id);
                    } else {
                        response = facade.getPersons();
                    }

                } catch (NumberFormatException nufe) {
                    response = "Id is not a number";
                    statusCode = 404;
                } catch (NotFoundException nofe) {
                    response = nofe.getMessage();
                    statusCode = 404;
                }
                break;

            case "POST":
                try {
                    InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String jsonQuery = br.readLine();

                    if (jsonQuery.contains("<") || jsonQuery.contains(">")) {
                        throw new IllegalArgumentException("No hacking please");
                    }

                    Person person = facade.addPerson(jsonQuery);
                    if (person.getPhone().length() > 50
                            || person.getfName().length() > 50
                            || person.getlName().length() > 70) {
                        throw new IllegalArgumentException("Input contains to many characters");
                    }

                    response = new Gson().toJson(person);
                } catch (IllegalArgumentException iae) {
                    statusCode = 400;
                    response = iae.getMessage();
                } catch (IOException e) {
                    statusCode = 500;
                    response = "Internal Server Problem";
                }
                break;

            case "PUT":
                try {
                    String path = he.getRequestURI().getPath();
                    int lastIndexOf = path.lastIndexOf("/");

                    if (lastIndexOf > 0) {
                        String idString = path.substring(lastIndexOf + 1);
                        int id = Integer.parseInt(idString);
                        String original = facade.getPerson(id);

                        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        String newValues = br.readLine();

                        if (newValues.contains("<") || newValues.contains(">")) {
                            throw new IllegalArgumentException("No hacking please");
                        }
                        
                        CharSequence subOriginal = original.subSequence(0, 11);
                        CharSequence subNewValues = newValues.subSequence(1, newValues.length());
                        String changed = "" + subOriginal + subNewValues;

                        Person person = facade.editPerson(changed);
                        if (person.getPhone().length() > 50
                                || person.getfName().length() > 50
                                || person.getlName().length() > 70) {
                            throw new IllegalArgumentException("Input contains to many characters");
                        }
                        response = new Gson().toJson(changed);
                    }

                } catch (NumberFormatException nufe) {
                    response = "Id is not a number";
                    statusCode = 404;
                } catch (NotFoundException nofe) {
                    response = nofe.getMessage();
                    statusCode = 404;
                }
                break;

            case "DELETE":
                try {
                    String path = he.getRequestURI().getPath();
                    int lastIndexOf = path.lastIndexOf("/");

                    if (lastIndexOf > 0) {
                        int id = Integer.parseInt(path.substring(lastIndexOf + 1));
                        Person deleted = facade.deletePerson(id);
                        response = new Gson().toJson(deleted);
                    } else {
                        statusCode = 400;
                        response = "<h1>Bad Request</h1>No id supplied with request";
                    }
                } catch (NotFoundException nofe) {
                    statusCode = 404;
                    response = nofe.getMessage();
                } catch (NumberFormatException nufe) {
                    response = "Id is not a number";
                    statusCode = 404;
                }
                break;
        }

        he.getResponseHeaders().add("Content-Type", "application/json");
        he.sendResponseHeaders(statusCode, 0);
        try (OutputStream os = he.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

}
