package server;

import com.sun.net.httpserver.HttpServer;
import exceptions.NotFoundException;
import handlers.FileHandler;
import handlers.PersonHandler;
import handlers.RoleHandler;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server 
{
    static int port = 9191;
    static String ip = "localhost";
    static String publicFolder = "web/";
    static String startFile = "daniel_page.html";
//    static String filesUri = "/pages";
    
    
    public void run() throws IOException, NotFoundException{
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
        server.createContext("/person" , new PersonHandler());
        server.createContext("/role" , new RoleHandler());
        server.createContext("/", new FileHandler());
//        server.createContext(filesUri, new FileHandler());
        server.start();
        System.out.println("Server is listening on port: " + port);
    }
    
    public static void main(String[] args) throws IOException, NotFoundException
    {
        if (args.length >= 3){
            port = Integer.parseInt(args[0]);
            ip = args[1];
            publicFolder = args[2];
        }
        new Server().run();
    }
    
}
