package buoi3_tcp_baitap1;

import java.net.*;
import java.io.*;

public class sever {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(679);
            System.out.println("Server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                String line = reader.readLine();
                System.out.println("Received: " + line);

                String result = convertNumberToWord(line);

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(result);
                out.flush();

                reader.close();
                out.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức chuyển đổi số thành chữ
    private static String convertNumberToWord(String number) {
        try {
            int num = Integer.parseInt(number);
            switch (num) {

                case 0:
                    return "khong";
                case 1:
                    return "mot";
                case 2:
                    return "hai";
                case 3:
                    return "ba";
                case 4:
                    return "bon";
                case 5:
                    return "nam";
                case 6:
                    return "sau";
                case 7:
                    return "bay";
                case 8:
                    return "tam";
                case 9:
                    return "chin";
                default:
                    return "khong tim thay";
            }
        } catch (NumberFormatException e) {
            return "Not a valid number";
        }
    }
}
