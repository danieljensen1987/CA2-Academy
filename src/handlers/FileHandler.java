package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileHandler implements HttpHandler
{

    @Override
    public void handle(HttpExchange he) throws IOException
    {
        {
            String contentFolder = "web/";
            String response = null;
            String error = null;
            String path = he.getRequestURI().getPath();
            int responseCode = 500;

            if (path.equals("/")) {
                path = "index.html";
            }

            File file = new File(contentFolder + path);
            byte[] bytesToSend = new byte[(int) file.length()];
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(bytesToSend, 0, bytesToSend.length);
                responseCode = 200;
            } catch (IOException ie) {
                responseCode = 404;
                error = "<!DOCTYPE html><html><body>"
                        + "<img src='gfx/error404.jpg'>"
                        + "<br>"
                        + "<iframe width='560' height='315' src='//www.youtube.com/embed/FyN9fAG_mIc?rel=0&autoplay=1' frameborder='0' allowfullscreen></iframe>"
                        + "</body></html>";
            }

            if (responseCode != 200) {
                bytesToSend = error.getBytes();
            }

            he.sendResponseHeaders(responseCode, bytesToSend.length);
            try (OutputStream os = he.getResponseBody()) {
                os.write(bytesToSend, 0, bytesToSend.length);
            }
        }
    }
}
