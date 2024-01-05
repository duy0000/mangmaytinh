package buoi5.bai2.dangki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class server {
    private static final int PORT = 8080;
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Đọc dữ liệu từ client
                String data = in.readLine();
                String[] credentials = data.split(",");

                if (credentials.length < 3 || credentials[0].isEmpty() || credentials[1].isEmpty() || credentials[2].isEmpty()) {
                    out.println("Vui long nhap du ten dang nhap, mat khau va xac nhan mat khau.");
                } else if (!credentials[1].equals(credentials[2])) {
                    out.println("Mat khau va xac nhan mat khau khong khop.");
                } else if (userExists(credentials[0])) {
                    out.println("Tai khoan da ton tai.");
                }
                else if (!checkpass(credentials[1])) {
                    out.println("Mat khau phai co it nhat 8 ky tu va chua it nhat mot chu so.");}
                else {
                        // Đăng ký thành công
                        User user = new User(credentials[0], credentials[1]);
                        users.add(user);
                        out.println("Dang ky thanh cong. xin chao: " + credentials[0]);
                    }

                    System.out.println("Danh sach tat ca nguoi dung:");
                    for (User u : users) {
                        System.out.println("Ten dang nhap: " + u.getUsername() + ", Mat khau: " + u.getPassword());
                    }
                // Đóng kết nối
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean checkpass(String password) {
        // Kiểm tra mật khẩu có ít nhất 8 ký tự và chứa ít nhất một chữ số
        String regex = "^(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

        private boolean userExists(String username) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
    }
}
