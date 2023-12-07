import java.io.*;

public class buoi2_bai2 {
    public static void main(String[] args) {
        try {
            // FileInputStream để đọc từ file
            InputStream inputStream = new FileInputStream("input.txt");
            StringBuilder reversedData = new StringBuilder();
            int data;
            //in nội dung file ra
            // while ((data = inputStream.read()) != -1) {
            //     // Chuyển đổi thành chữ hoa và in ra màn hình
            //     char charData = (char) data;
            //     System.out.print(charData);
            // }

            //in ra và viết hoa
            while ((data = inputStream.read()) != -1) {
                // Chuyển đổi thành chữ hoa và in ra màn hình
                char charData = (char) data;
                charData = Character.toUpperCase(charData);
                System.out.print(charData);
            }

            //in ra và đảo đảo ngược
            // while ((data = inputStream.read()) != -1) {
            //     // Append giá trị ký tự vào StringBuilder
            //     char charData = (char) data;
            //     reversedData.append(charData);
            // }
            // // Đảo ngược chuỗi
            // reversedData.reverse();
            // // In ra màn hình chuỗi đảo ngược
            // System.out.println(reversedData.toString());
            // // Đóng InputStream
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}