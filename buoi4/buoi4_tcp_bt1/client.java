package buoi4_tcp_bt1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.1.38", 6799);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                Integer userInput = checkDieuKien(scanner);
                if (userInput == null) {
                    System.out.println("Thoat chuong trinh theo yeu cau nguoi dung.");
                    break;
                }

                System.out.println("Du lieu nguoi dung nhap tu ban phim: " + userInput);
                out.println(String.valueOf(userInput));

                // Đọc dữ liệu từ server bằng BufferedReader
                String receivedLine = reader.readLine();
                System.out.println("Du lieu tu server: " + receivedLine);

                if (receivedLine != null && receivedLine.equalsIgnoreCase("khong tim thay")) {
                    System.out.println("Thoat chuong trinh theo yeu cau nguoi dung.");
                    break; // Thoát vòng lặp nếu server trả về "khong tim thay"
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer checkDieuKien(Scanner scanner) {
        while (true) {
            try {
                System.out.println("-----------------MENU-----------------");
                System.out.println("1. Kiem tra chan le.");
                System.out.println("2. Kiem tra nam nhuan.");
                System.out.print("Nhap vao mot so tu 1 den 2 (nhap exit de thoat): ");
                String inputLine = scanner.next();

                if (inputLine.equalsIgnoreCase("exit")) {
                    // Thoát nếu người dùng nhập 'exit'
                    return null;
                }

                Integer userInput = Integer.parseInt(inputLine);

                if (userInput >= 1 && userInput <= 2) {
                    // Số hợp lệ, thoát khỏi vòng lặp nhập số
                    return userInput;
                } else {
                    System.out.println("So khong hop le. Vui long nhap lai.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Khong phai la so nguyen. Vui long nhap lai.");
            }
        }
    }
}

