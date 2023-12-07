package buoi3_tcp;

import java.net.*;
import java.io.*;

public class sever {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6789);
            System.out.println("server is ok");
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                String line = reader.readLine();
                System.out.println("received: " + line);

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(line.toUpperCase());
                out.flush();

                reader.close();
                out.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
