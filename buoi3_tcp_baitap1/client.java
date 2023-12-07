package buoi3_tcp_baitap1;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        try {
            while (true) {
                Socket socket = new Socket("127.0.0.1", 679);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in);

                // Lặp cho đến khi người dùng nhập một số từ 0 đến 9
                int userInput;
                while (true) {
                    try {
                        System.out.print("Nhap vao mot so tu 0 den 9 (nhap 'exit' de thoat): ");
                        String inputLine = scanner.nextLine();

                        if (inputLine.equalsIgnoreCase("exit")) {
                            // Thoát nếu người dùng nhập 'exit'
                            return;
                        }

                        userInput = Integer.parseInt(inputLine);

                        if (userInput >= 0 && userInput <= 9) {
                            // Số hợp lệ, thoát khỏi vòng lặp nhập số
                            break;
                        } else {
                            System.out.println("So khong hop le. Vui long nhap lai.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Khong phai la so nguyen. Vui long nhap lai.");
                    }
                }

                System.out.println("Du lieu nguoi dung nhap tu ban phim: " + userInput);
                out.println(userInput);

                String receivedLine = reader.readLine();
                System.out.println("Du lieu tu server: " + receivedLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
