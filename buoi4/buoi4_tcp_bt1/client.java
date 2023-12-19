package buoi4_tcp_bt1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class client {
    public static void main(String[] args) {
        try {
            // Tạo Socket để kết nối đến server
            Socket clientSocket = new Socket("localhost", 9876);

            // Luồng đọc dữ liệu từ người dùng
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // Luồng ghi dữ liệu đến server
            OutputStream outputStream = clientSocket.getOutputStream();

            while (true) {
                System.out.println("-----------------MENU-----------------");
                System.out.println("1. Kiem tra chan le.");
                System.out.println("2. Kiem tra nam nhuan.");
                System.out.println("3. Tinh so ngay cua mot thang.");
                System.out.println("4. Hien thi thu trong tuan.");
                System.out.println("5. Viet hoa mot chuoi.");
                System.out.println("6. Dao nguoc chuoi.");
                System.out.println("7. In ra cac so nguyen to <= n.");
                System.out.println("Nhap du lieu de gui xang sever (nhap 'exit' de thoat): ");
                System.out.println("vi du : 1,203 ");

                // Nhập dữ liệu từ người dùng
                String userInputString = userInput.readLine();

                // Kiểm tra điều kiện dừng
                if (userInputString.equalsIgnoreCase("exit")) {
                    break;
                }

                // Gửi dữ liệu đến server
                outputStream.write(userInputString.getBytes());
                outputStream.write('\n'); // Thêm ký tự xuống dòng để đánh dấu kết thúc dữ liệu

                // Đọc phản hồi từ server
                BufferedReader serverResponse = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String response = serverResponse.readLine();
                
                // Hiển thị phản hồi từ server
                System.out.println("");
                System.out.println("Du lieu lay tu server: " + response);
                System.out.println("");
            }

            // Đóng socket
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
