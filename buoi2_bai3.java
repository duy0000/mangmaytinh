import java.io.FileWriter;
import java.io.IOException;

public class buoi2_bai3 {
    public static void main(String[] args) {
        try {
            // Tạo một FileWriter để ghi kết quả vào file output.txt
            FileWriter fileWriter = new FileWriter("output.txt");

            // Ghi thông báo vào file
            fileWriter.write("Nghiệm của phương trình 2x^2 + 5x + 6 = 0 là:\n");

            // Gọi hàm để tìm nghiệm và ghi vào file
            // solveQuadraticEquation(fileWriter);
               // Hệ số của phương trình bậc 2: ax^2 + bx + c = 0
        double a = 2;
        double b = 5;
        double c = 6;

        // Tính delta
        double delta = b * b - 4 * a * c;

        if (delta < 0) {
            // Phương trình vô nghiệm
            fileWriter.write("Phương trình vô nghiệm.");
        } else if (delta == 0) {
            // Phương trình có nghiệm kép
            double root = -b / (2 * a);
            fileWriter.write("Phương trình có nghiệm kép x = " + root);
        } else {
            // Phương trình có hai nghiệm phân biệt
            double root1 = (-b + Math.sqrt(delta)) / (2 * a);
            double root2 = (-b - Math.sqrt(delta)) / (2 * a);
            fileWriter.write("Phương trình có hai nghiệm phân biệt:\n");
            fileWriter.write("x1 = " + root1 + "\n");
            fileWriter.write("x2 = " + root2);
        }

            // Đóng FileWriter
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm để tìm nghiệm của phương trình bậc 2 và ghi vào file
    // private static void solveQuadraticEquation(FileWriter fileWriter) throws IOException {
    //     // Hệ số của phương trình bậc 2: ax^2 + bx + c = 0
    //     double a = 2;
    //     double b = 5;
    //     double c = 6;

    //     // Tính delta
    //     double delta = b * b - 4 * a * c;

    //     if (delta < 0) {
    //         // Phương trình vô nghiệm
    //         fileWriter.write("Phương trình vô nghiệm.");
    //     } else if (delta == 0) {
    //         // Phương trình có nghiệm kép
    //         double root = -b / (2 * a);
    //         fileWriter.write("Phương trình có nghiệm kép x = " + root);
    //     } else {
    //         // Phương trình có hai nghiệm phân biệt
    //         double root1 = (-b + Math.sqrt(delta)) / (2 * a);
    //         double root2 = (-b - Math.sqrt(delta)) / (2 * a);
    //         fileWriter.write("Phương trình có hai nghiệm phân biệt:\n");
    //         fileWriter.write("x1 = " + root1 + "\n");
    //         fileWriter.write("x2 = " + root2);
    //     }
    // }
}
