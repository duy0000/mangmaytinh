import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 995; // POP3 port

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Đọc phản hồi từ server
            System.out.println("Server response: " + in.readLine());

            // Gửi lệnh USER để đăng nhập
            sendCommand(out, "USER user1@gmail.com");

            // Gửi lệnh PASS để nhập mật khẩu
            sendCommand(out, "PASS password1");

            // Gửi lệnh QUIT để đóng kết nối
            sendCommand(out, "QUIT");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendCommand(PrintWriter out, String command) {
        out.println(command);
        System.out.println("Client: " + command);
    }
}
