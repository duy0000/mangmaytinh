package buoi8.buoi8_bai1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final int SERVER_PORT = 2001;
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
        String clientName = null;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientName = in.readLine(); // Assume the first message is the client's name

            // Kiểm tra tên người dùng có chứa dấu không và ngắt kết nối nếu có
            if (containsDiacritics(clientName)) {
                System.out.println("Client " + clientName + " disconnected due to invalid name.");
                return;
            }

            System.out.println("Client " + clientName + " connected from: " + clientSocket.getInetAddress());

            // Hiển thị thông báo cho tất cả client khi có người dùng mới kết nối
            broadcastMessage(clientName + " online");

            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/sendfile")) {
                    handleFileTransfer(clientName, in);
                } else {
                    broadcastMessage(clientName + ": " + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                clientWriters.removeIf(writer -> writer.checkError());

                // Hiển thị thông báo cho tất cả client khi có người dùng thoát
                if (clientName != null) {
                    broadcastMessage(clientName + " offline");
                }

                System.out.println("Client disconnected.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleFileTransfer(String senderName, BufferedReader in) {
        try {
            String fileName = in.readLine(); // giả sử dòng tiếp theo chứa tên file
            String filePath = "D:\\quantrimang\\buoi8\\buoi8_bai1\\file" + fileName; // Đặt đường dẫn tệp tin mong muốn
    
            Path destinationPath = Path.of(filePath);
    
            // Tạo BufferedWriter để ghi dữ liệu vào tệp tin
            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(destinationPath.toFile()))) {
                String line;
                while ((line = in.readLine()) != null && !line.equals("/endfile")) {
                    fileWriter.write(line);
                    fileWriter.newLine(); // Thêm ký tự xuống dòng
                }
            }
    
            broadcastMessage(senderName + " đã gửi một tệp tin: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    private boolean containsDiacritics(String name) {
        // Kiểm tra xem tên có chứa dấu không sử dụng regex
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        return !normalized.matches("\\p{ASCII}+");
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
        try (PrintWriter historyWriter = new PrintWriter(new FileWriter("D:\\quantrimang\\buoi8\\buoi8_bai1\\history.txt", true))) {
            historyWriter.println(message);
            historyWriter.flush();
            System.out.println("Message written to history file: " + message);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing to history file: " + e.getMessage());
        }
    }
}
