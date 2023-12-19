package buoi4_tcp_bt1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class sever {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9876)) {
            System.out.println("Server running...");

            while (true) {
                // Chấp nhận kết nối từ client
                Socket clientSocket = serverSocket.accept();
                System.out.println("da ket noi voi client: " + clientSocket.getInetAddress());

                // Tạo luồng đọc và ghi dữ liệu từ/to client
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                while (true) {
                    // Đọc dữ liệu từ client
                    String clientData = reader.readLine();
                    System.out.println("du lieu tu client: " + clientData);

                    // Kiểm tra điều kiện dừng
                    if (clientData.equalsIgnoreCase("exit")) {
                        break;
                    }

                    // Xử lý dữ liệu
                    String[] inputArray = clientData.split(",");
                    int choice = Integer.parseInt(inputArray[0].trim());
                    String result = "";
                    if(inputArray.length==2){

                        switch (choice) {
                            case 1:
                               
                                result =kiemTraChanLe(inputArray[1]);
                                break;
                            case 2:
                                    
                                result = kiemTraNamNhuan(inputArray[1]);
                                break;
                            
                             case 4:
                                    
                                result = "dang phat trien";
                                break;
                             case 5:
                                    
                                result = "dang phat trien";
                                break;
                            case 6:
                                
                                result = "dang phat trien";
                                break;
                            case 7:
                                
                                result = "dang phat trien";
                                break;    
                            default:
                            result = "khong tim thay lua chon "+inputArray[0];
                            break;
                            }
                        }
                        else if (inputArray.length==3){
                            switch (choice) {
                            case 3:
                                
                                result = tinhSoNgayTrongThang(inputArray[1], inputArray[2]);
                                break;
                            }
                        }
                    else{
                        out.println("do dai input khong hop le vui long nhap lai");
                    }
                    // Thực hiện các thao tác tùy thuộc vào lựa chọn

                    // Gửi kết quả về client
                    out.println(result);
                }

                // Đóng kết nối với client hiện tại
                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String kiemTraChanLe(String numberStr) {
        try {
            int number = Integer.parseInt(numberStr.trim());
            return (number % 2 == 0) ? "So chan" : "So le";
        } catch (NumberFormatException e) {
            return "Loi: Dau vao khong phai la so nguyen";
        }
    }

    private static String kiemTraNamNhuan(String yearStr) {
        try {
            int year = Integer.parseInt(yearStr.trim());
            boolean isLeapYear = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
            return isLeapYear ? "La nam nhuan" : "Khong phai la nam nhuan";
        } catch (NumberFormatException e) {
            return "Loi: Dau vao khong phai la so nguyen";
        }
    }

    private static String tinhSoNgayTrongThang(String monthStr, String yearStr) {
        try {
            int month = Integer.parseInt(monthStr.trim());
            int year = Integer.parseInt(yearStr.trim());

            if (month < 1 || month > 12) {
                return "Loi: Thang khong hop le";
            }

            int daysInMonth;
            if (month == 2) {
                daysInMonth = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                daysInMonth = 30;
            } else {
                daysInMonth = 31;
            }

            return "Thang " + month + " nam " + year + " co " + daysInMonth + " ngay.";
        } catch (NumberFormatException e) {
            return "Loi: dau vao khong phai la so nguyen";
        }
    }

  
}
