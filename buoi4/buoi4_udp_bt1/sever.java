import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class sever {

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(9876)) {
            System.out.println("Server is running...");

            byte[] receiveData = new byte[4096];

            while (true) {
                // Create a packet to receive data
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Receive data from the client
                serverSocket.receive(receivePacket);

                // Convert data from bytes to string and display
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("du lieu lay tu client " + clientMessage);

                // Process the message and respond to the client
                String responseMessage = processMessage(clientMessage);
                System.out.println("du lieu tra ve client: " + responseMessage);

                // Respond to the client with the processed message
                byte[] sendData = responseMessage.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            System.out.println( "An error occurred in the server.");
        }
    }

    private static String processMessage(String message) {
        try {
            int number = Integer.parseInt(message); // Đưa khai báo của 'number' ra khỏi điều kiện
            if (number < 100) {
                List<Integer> primes = findPrimes(number);
                return primes.toString();
            } else {
                return "So phai nho hon 100.";
            }
        } catch (NumberFormatException e) {
            return "Vui long nhap so.";
        }
    }

    private static List<Integer> findPrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i < n; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
