import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server {

    private static final int PORT = 995; // POP3 SSL port

    public static void main(String[] args) {
        try {
            ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(PORT);

            System.out.println("Secure POP3 Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

                Thread clientHandler = new Thread(() -> handleClient(clientSocket));
                clientHandler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            out.println("+OK Secure POP3 Server Ready");

            String userInput;
            while ((userInput = in.readLine()) != null) {
                System.out.println("Client: " + userInput);

                if (userInput.startsWith("USER")) {
                    out.println("+OK User accepted");
                } else if (userInput.startsWith("PASS")) {
                    // Implement authentication logic here
                    out.println("-ERR Authentication not implemented");
                } else if (userInput.equals("QUIT")) {
                    out.println("+OK Bye");
                    break;
                } else {
                    out.println("-ERR Unknown command");
                }
            }

            clientSocket.close();
            System.out.println("Connection closed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
