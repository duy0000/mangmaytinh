package buoi5.bai1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

public class client {
    public static void main(String[] args) {
        try {
            // Cứng địa chỉ IP của server (ví dụ: localhost)
            String serverAddress = "localhost";

            while (true) {
                // Kết nối tới server
                Socket socket = new Socket(serverAddress, 8080);

                // Xử lý luồng đầu vào và đầu ra từ server
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Yêu cầu người dùng nhập tên
                String clientName = JOptionPane.showInputDialog("Nhập tên của bạn :");
                if (clientName == null) {
                    // Nếu người dùng bấm cancel, thoát khỏi vòng lặp
                    break;
                }

                // Gửi tên tới server
                out.println(clientName);

                // Đọc dữ liệu từ server
                String serverResponse = in.readLine();

                // Hiển thị dữ liệu từ server
                JOptionPane.showMessageDialog(null, serverResponse);

                // Đóng kết nối
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
