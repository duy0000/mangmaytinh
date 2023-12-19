package buoi4_udp_bt2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class sever {

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(9876)) {
            System.out.println("Server is running...");
    
            byte[] receiveData = new byte[4096];
    
            while (true) {
                // Tạo một gói để nhận dữ liệu
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    
                // Nhận dữ liệu từ client
                serverSocket.receive(receivePacket);
    
                // Chuyển đổi dữ liệu từ dạng byte sang chuỗi và hiển thị
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Du lieu lay tu client: " + clientMessage);
    
                // Kiểm tra chiều dài của dữ liệu đầu vào
                if (clientMessage != null && clientMessage.split(",").length == 2) {
                    // Xử lý thông điệp và phản hồi lại client
                    String responseMessage = processMessage(clientMessage);    
                    // Phản hồi lại client với thông điệp đã xử lý
                    byte[] sendData = responseMessage.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    serverSocket.send(sendPacket);
                } else {
                    // Chiều dài dữ liệu không hợp lệ, gửi thông điệp về client
                    String errorMessage = "Chieu dai du lieu khong hop le, vui long nhap lai.";
                    byte[] errorData = errorMessage.getBytes();
                    DatagramPacket errorPacket = new DatagramPacket(errorData, errorData.length, receivePacket.getAddress(), receivePacket.getPort());
                    serverSocket.send(errorPacket);
                }
            }
        } catch (Exception e) {
            System.out.println("Da xay ra loi trong server.");
        }
    }
    
    

    private static String processMessage(String message) {
        String[] inputArray = message.split(",");
        String responseMessage = "";
        try {
            int choice = Integer.parseInt(inputArray[0]);
            
            switch (choice) {
                case 1:
                    responseMessage = processOption1(inputArray);
                    break;
                case 2:
                    responseMessage = processOption2(inputArray);
                    break;
                case 3:
                    responseMessage = processOption3(inputArray);
                    break;
                default:
                    responseMessage = "khong tim thay lua chon "+ inputArray[0]+" !!!  vui long nhap lai.";
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            responseMessage = "ki tu dau phai la so.";
        }

        return responseMessage;
    }

    private static String processOption1(String[] inputArray) {
        try {
            // Lấy giá trị cần xử lý từ mảng
            String dataToProcess = inputArray[1];
    
            // Thực hiện xử lý tùy chọn 1 trên dữ liệu
            String processedData = removeWhiteSpace(dataToProcess);
    
            // Trả về kết quả xử lý
            return processedData;
        } catch (ArrayIndexOutOfBoundsException e) {
            return "input khong hop le.";
        }
    }
    
    private static String removeWhiteSpace(String input) {
        // Loại bỏ khoảng trắng từ chuỗi
        return input.replaceAll("\\s", "");
    }
    

    private static String processOption2(String[] inputArray) {
        try {
            // Lấy giá trị cần xử lý từ mảng
            String dataToProcess = inputArray[1];
    
            // Thực hiện xử lý tùy chọn 2 trên dữ liệu
            String processedData = capitalizeFirstCharacter(dataToProcess);
    
            // Trả về kết quả xử lý
            return processedData;
        } catch (ArrayIndexOutOfBoundsException e) {
            return"input khong hop le.";
        }
    }
    
    private static String capitalizeFirstCharacter(String input) {
        // Viết hoa ký tự đầu tiên của chuỗi
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }
    

    private static String processOption3(String[] inputArray) {
        try {
            // Lấy giá trị cần xử lý từ mảng
            String dataToProcess = inputArray[1];
    
            // Thực hiện xử lý tùy chọn 3 trên dữ liệu
            boolean isPalindrome = isPalindrome(dataToProcess);
    
            // Trả về kết quả xử lý
            return(isPalindrome ? "doi xung" : "khong doi xung");
        } catch (ArrayIndexOutOfBoundsException e) {
            return "input khong hop le.";
        }
    }
    
    private static boolean isPalindrome(String input) {
        // Loại bỏ khoảng trắng và chuyển về chữ thường
        String cleanInput = input.replaceAll("\\s", "").toLowerCase();
    
        // So sánh chuỗi gốc và chuỗi đảo ngược
        return cleanInput.equals(new StringBuilder(cleanInput).reverse().toString());
    }
    
}
