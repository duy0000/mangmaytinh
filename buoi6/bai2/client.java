package buoi6.bai2;

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
                Socket socket = new Socket(serverAddress, 8000);

                // Xử lý luồng đầu vào và đầu ra từ server
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Yêu cầu người dùng nhập mã sinh viên
                String studentId = getStudentIdFromUser();
                if (studentId == null) {
                    // Nếu người dùng bấm cancel, thoát khỏi vòng lặp
                    break;
                }

                // Gửi mã sinh viên tới server
                out.println(studentId);

                // Đọc phản hồi từ server
                String serverResponse = in.readLine();

                // Hiển thị phản hồi từ server
                JOptionPane.showMessageDialog(null, serverResponse);

                // Đóng kết nối
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getStudentIdFromUser() {
        String studentId = JOptionPane.showInputDialog("Nhập mã sinh viên:");
        if (studentId == null) {
            return null; // Người dùng bấm cancel
        }

        return studentId;
    }
}


