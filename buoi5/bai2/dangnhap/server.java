package buoi5.bai2.dangnhap;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class server {
    private static final Map<String, String> accountDatabase = new HashMap<>();

    static {
        // Thêm các cặp tài khoản và mật khẩu vào danh sách
        accountDatabase.put("u1", "p1");
        accountDatabase.put("user2", "pass2");
        // Thêm các tài khoản khác nếu cần
    }

    private static boolean checkemail1(String input) {
        return input.contains("@");
    }

    private static boolean checkemail2(String input) {
        return input.contains(".");
    }

    private static boolean checkCredentials(String username, String password) {
        // Kiểm tra tài khoản và mật khẩu từ danh sách
        return accountDatabase.containsKey(username) && accountDatabase.get(username).equals(password);
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server is listening on port 8080...");
//-------------để serverr luôn luôn chạy chỉ với 1 lần khởi động
            while (true) {
                // Chờ client kết nối
                Socket clientSocket = serverSocket.accept();

                // Tạo một luồng riêng cho mỗi kết nối
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            // Xử lý luồng đầu vào và đầu ra từ client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Đọc tài khoản và mật khẩu từ client
            String[] credentials = in.readLine().split(",");

                // Kiểm tra xem tên đăng nhập và mật khẩu có trống không
                if (credentials.length < 2 || credentials[0].trim().isEmpty() || credentials[1].trim().isEmpty()) {
                    out.println("Ten dang nhap hoac mat khau khong duoc de trong");
                } 
                
                else {
                    // Kiểm tra tài khoản và mật khẩu từ danh sách
                    if (checkCredentials(credentials[0], credentials[1])) {
                        out.println("Xin chào, " + credentials[0] );
                    }
                         else {
                        out.println("Tai khoan hoac mat khau khong dung.");
                    }
                }
            // Đóng kết nối với client
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
