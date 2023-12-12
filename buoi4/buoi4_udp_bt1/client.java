
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        try {
            // Tạo DatagramSocket
            DatagramSocket clientSocket = new DatagramSocket();

            // Nhập dữ liệu từ người dùng
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Nhap du lieu de gui xang sever (nhap 'exit' de thoat): ");
                String message = scanner.nextLine();

                // Kiểm tra điều kiện dừng
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                // Chuyển dữ liệu thành byte
                byte[] sendData = message.getBytes();

                // Lấy địa chỉ IP của server (trong trường hợp này là localhost)
                InetAddress serverAddress = InetAddress.getByName("localhost");

                // Tạo DatagramPacket để gửi dữ liệu đến server
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9876);

                // Gửi dữ liệu đến server
                clientSocket.send(sendPacket);

                // Nhận phản hồi từ server
                byte[] receiveData = new byte[4096];
                DatagramPacket receivePacket = new DatagramPacket (receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                // Chuyển dữ liệu từ byte sang chuỗi và hiển thị
                String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Du lieu lay tu server: " + serverResponse);
            }

            // Đóng socket
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
