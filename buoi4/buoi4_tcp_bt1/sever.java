package buoi4_tcp_bt1;

import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class sever {
    private static PrintWriter out; // Add a PrintWriter field

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6799);
            System.out.println("Server is running...");

            while (true) {
                Socket socket = serverSocket.accept();
                out = new PrintWriter(socket.getOutputStream(), true);
                handleClient(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            System.out.println("Received: " + line);

            String result = convertNumberToWord(line);

            out.println(result);

            // Close resources outside the loop
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertNumberToWord(String number) {
        try {
            int num = Integer.parseInt(number);
            switch (num) {
                case 1:
                    EvenOddChecker();
                    break;
                case 2:
                    kiemTraNamNhuan();
                    break;
                default:
                    return "khong tim thay";
            }
        } catch (NumberFormatException e) {
            return "Not a valid number";
        }
        return "Some default value";
    }

    public static void EvenOddChecker() {
        Scanner scanner = new Scanner(System.in);
        int number;

        out.println("Nhap mot so nguyen: ");

        while (!scanner.hasNextInt()) {
            out.println("Vui long chi nhap so nguyen.");
            scanner.next();
        }
        number = scanner.nextInt();

        out.println("--------------------------------");
        if (number % 2 == 0) {
            out.println(number + " la so chan.");
        } else {
            out.println(number + " la so le.");
        }
        out.println("----------------------------------------------------");
    }


    public static void kiemTraNamNhuan() {
        Scanner scanner = new Scanner(System.in);
        int year;

        System.out.print("Nhap mot nam de kiem tra xem no co phai la nam nhuan hay khong: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Vui long chi nhap mot so.");
            scanner.next();
        }
        year = scanner.nextInt();

        System.out.println("--------------------------------");
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            System.out.println(year + " la nam nhuan.");
        } else {
            System.out.println(year + " khong phai la nam nhuan.");
        }
         System.out.println("----------------------------------------------------");
    }
}
