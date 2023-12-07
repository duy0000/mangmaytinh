// viet file để kết nối với sever để viết hoa chữ


// package buoi3_tcp;

// import java.net.*;
// import java.io.*;
// import java.util.Scanner;

// public class client {
//     public static void main(String[] args) {
//         try {
//             Socket socket = new Socket("127.0.0.1",6789);
//             BufferedReader reader=
//             new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out=new PrintWriter(socket.getOutputStream());
//             Scanner s =new Scanner(System.in);
//             System.out.print("enter a line: ");
//             String st=s.nextLine();
//             System.out.println("Chuoi nhap tu ban phim: "+st);
//             out.println(st);
//             out.flush();
//             String line=reader.readLine();
//             System.out.println("sau khi viet hoa: "+line);
//             reader.close();
//             out.close();
//             socket.close();
//         } catch (Exception e) {
//             // TODO: handle exception
//             e.printStackTrace();
//         }
//     }
// }


package buoi3_tcp;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        try {
            while (true) {
                Socket socket = new Socket("127.0.0.1", 6789);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in);

                System.out.print("nhap vao du lieu (nhap 'exit' de thoat): ");
                String inputLine = scanner.nextLine();

                if (inputLine.equalsIgnoreCase("exit")) {
                    break; // Thoát vòng lặp nếu người dùng nhập 'exit'
                }

                System.out.println("Du lieu nguoi dung nhap tu ban phim: " + inputLine);
                out.println(inputLine);

                String receivedLine = reader.readLine();
                System.out.println("Nhap tu server: " + receivedLine);

                reader.close();
                out.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

