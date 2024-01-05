package buoi5.bai2.dangki;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

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

                // Yêu cầu người dùng nhập tài khoản, mật khẩu và xác nhận mật khẩu
                String[] credentials = getRegistrationInfoFromUser();
                if (credentials == null) {
                    // Nếu người dùng bấm cancel, thoát khỏi vòng lặp
                    break;
                }

                // Gửi tài khoản, mật khẩu và xác nhận mật khẩu tới server
                out.println(credentials[0] + "," + credentials[1] + "," + credentials[2] + "," + "register");

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

    private static String[] getRegistrationInfoFromUser() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        Object[] message = {
                "Nhập tài khoản:", usernameField,
                "Nhập mật khẩu:", passwordField,
                "Xác nhận mật khẩu:", confirmPasswordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Đăng ký", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            char[] passwordChars = passwordField.getPassword();
            char[] confirmPasswordChars = confirmPasswordField.getPassword();

            String username = usernameField.getText();
            String password = new String(passwordChars);
            String confirmPassword = new String(confirmPasswordChars);

            // Clear the password data to enhance security
            Arrays.fill(passwordChars, ' ');
            Arrays.fill(confirmPasswordChars, ' ');

            return new String[]{username, password, confirmPassword};
        } else {
            return null; // Người dùng bấm cancel
        }
    }
}
