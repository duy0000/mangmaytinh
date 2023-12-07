//check name vs ip của máy tính
// import java.net.InetAddress;
// import java.net.UnknownHostException;

// public class buoi3 {
//     public static void main(String[] args) {
//         try {
//             // Lấy InetAddress của localhost
//             InetAddress localhost = InetAddress.getLocalHost();

//             String ipAddress = localhost.getHostAddress();
//             String hostname = localhost.getHostName();
//             // Hiển thị địa chỉ IP của localhost
//             System.out.println("host address : " + ipAddress);
//             System.out.println("host name : " + hostname);
//         } catch (UnknownHostException e) {
//             e.printStackTrace();
//         }
//     }
// }


//check ip và name của web
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class buoi3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhap ten trang web (vi du: google.com.vn): ");
        String nameweb = scanner.nextLine();

        try {
            InetAddress[] addresses = InetAddress.getAllByName(nameweb);
            for (int i = 0; i < addresses.length; i++) {
                System.out.println("Dia chi IP " + (i + 1) + " : " + addresses[i]);
            }
        } catch (UnknownHostException e) {
            System.out.println("Khong tim thay dia chi IP cho trang web " + nameweb);
        }
        scanner.close();
    }
}


