//lấy dữ liệu từ file excel để xuất vào server

package buoi6.bai2;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class server {
    private static final Map<String, String[]> studentDatabase = new HashMap<>();

    static {
        try {
            readStudentDataFromExcel("path/to/your/excel/file.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readStudentDataFromExcel(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            for (Row row : sheet) {
                String studentId = getCellValueAsString(row.getCell(0));
                String name = getCellValueAsString(row.getCell(1));
                String dob = getCellValueAsString(row.getCell(2));
                String className = getCellValueAsString(row.getCell(3));

                studentDatabase.put(studentId, new String[]{name, dob, className});
            }
        }
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server is listening on port 8000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String studentId = in.readLine();

            if (studentId.trim().isEmpty()) {
                out.println("Mã sinh viên không được để trống");
            } else {
                try {
                    Long.parseLong(studentId);
                    if (!checkStudentId(studentId)) {
                        out.println("Mã sinh viên phải là chuỗi và có 10 chữ số");
                    } else {
                        if (checkStudent(studentId)) {
                            String[] studentInfo = studentDatabase.get(studentId);
                            out.println("Thông tin sinh viên: Mã sinh viên " + studentId +
                                    ", Họ và tên: " + studentInfo[0] +
                                    ", Ngày sinh: " + studentInfo[1] +
                                    ", Lớp: " + studentInfo[2]);
                        } else {
                            out.println("Không tìm thấy thông tin cho mã sinh viên: " + studentId);
                        }
                    }
                } catch (NumberFormatException ex) {
                    out.println("Mã sinh viên không hợp lệ. Vui lòng chỉ nhập số.");
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkStudentId(String studentId) {
        return studentId.matches("\\d{10}");
    }

    private static boolean checkStudent(String studentId) {
        return studentDatabase.containsKey(studentId);
    }
}

