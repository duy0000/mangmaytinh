package buoi5.bai2.dangnhap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

// public class client {
//     public static void main(String[] args) {
//         try {
//             // Cứng địa chỉ IP của server (ví dụ: localhost)
//             String serverAddress = "localhost";

//             while (true) {
//                 // Kết nối tới server
//                 Socket socket = new Socket(serverAddress, 8080);

//                 // Xử lý luồng đầu vào và đầu ra từ server
//                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

//                 // Yêu cầu người dùng nhập tài khoản và mật khẩu
//                 String[] credentials = getCredentialsFromUser();
//                 if (credentials == null) {
//                     // Nếu người dùng bấm cancel, thoát khỏi vòng lặp
//                     break;
//                 }

//                 // Gửi tài khoản và mật khẩu tới server
//                 out.println(credentials[0] + "," + credentials[1]);

//                 // Đọc phản hồi từ server
//                 String serverResponse = in.readLine();

//                 // Hiển thị phản hồi từ server
//                 JOptionPane.showMessageDialog(null, serverResponse);

//                 // Đóng kết nối
//                 socket.close();
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     private static String[] getCredentialsFromUser() {
//         String username = JOptionPane.showInputDialog("Nhập tài khoản:");
//         if (username == null) {
//             return null; // Người dùng bấm cancel
//         }

//         String password = JOptionPane.showInputDialog("Nhập mật khẩu:");
//         if (password == null) {
//            return null;
           
//         }

//         return new String[]{username, password};
//     }
// }





import javax.swing.*;
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

                // Yêu cầu người dùng nhập tài khoản và mật khẩu
                String[] credentials = getCredentialsFromUser();
                if (credentials == null) {
                    // Nếu người dùng bấm cancel, thoát khỏi vòng lặp
                    break;
                }

                // Gửi tài khoản và mật khẩu tới server
                out.println(credentials[0] + "," + credentials[1]);

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

    private static String[] getCredentialsFromUser() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        // JTextField emailField=new JTextField();
        Object[] message = {
                "Nhập tài khoản:", usernameField,
                "Nhập mật khẩu:", passwordField,
                // "nhập email",emailField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Đăng nhập", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            char[] passwordChars = passwordField.getPassword();
            String username = usernameField.getText();
            String password = new String(passwordChars);
            // String email=emailField.getText();
            
            // Clear the password data to enhance security
            Arrays.fill(passwordChars, ' ');

            return new String[]{username, password};
        } else {
            return null; // Người dùng bấm cancel
        }
    }
}
