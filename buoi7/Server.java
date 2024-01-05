package buoi7;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final int SERVER_PORT = 2002;
    private static CopyOnWriteArrayList<PrintWriter> clientWriters = new CopyOnWriteArrayList<>();
    private volatile boolean serverRunning = true;

    public static void main(String[] args) {
        try {
            new Server().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() throws IOException {
        System.out.println("Server is running...");

        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

        while (serverRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(clientWriter);

                new Thread(() -> handleClient(clientSocket)).start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Đóng serverSocket khi thoát vòng lặp
        serverSocket.close();
        System.out.println("Server is stopped.");
    }

    private void handleClient(Socket clientSocket) {
        String clientName = null;  // Khai báo ở mức độ của phương thức
    
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientName = in.readLine(); // Assume the first message is the client's name
            System.out.println("Client " + clientName + " connected from: " + clientSocket.getInetAddress());
    
            // Hiển thị thông báo cho tất cả client khi có người dùng mới kết nối
            broadcastMessage(clientName + " online.");
    
            String message;
            while ((message = in.readLine()) != null) {
                broadcastMessage(clientName + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                clientWriters.removeIf(writer -> writer.checkError()); // Loại bỏ người viết có lỗi
    
                // Hiển thị thông báo cho tất cả client khi có người dùng thoát
                if (clientName != null) {
                    broadcastMessage(clientName + " off line.");
                }
    
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    


    private void broadcastMessage(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timestamp = sdf.format(new Date());
        String formattedMessage = "[" + timestamp + "] " + message;

        // In ra tin nhắn vào console
        System.out.println(formattedMessage);

        // Lưu tin nhắn vào tệp tin "history.txt"
        saveToHistoryFile(formattedMessage);

        // Gửi tin nhắn đến tất cả các client
        for (PrintWriter writer : clientWriters) {
            writer.println(formattedMessage);
        }
    }

    private void saveToHistoryFile(String message) {
        try (PrintWriter historyWriter = new PrintWriter(new FileWriter("D:\\quantrimang\\buoi7\\history.txt", true))) {
            historyWriter.println(message);
            historyWriter.flush();  // Đảm bảo rằng dữ liệu được ghi ngay lập tức
            System.out.println("Message written to history file: " + message);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing to history file: " + e.getMessage());
        }
    }
    
    

}
