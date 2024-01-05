 package buoi6.bai1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class server {
    private static final Map<String, String[]> studentDatabase = new HashMap<>();

    static {
        studentDatabase.put("2055010002", new String[]{"Nguyen Van An", "11/1/2002", "2020CN2"});
        studentDatabase.put("2055010008", new String[]{"Do Thi Phuong Anh", "5/6/2002", "2020CN2"});
        studentDatabase.put("2055010014", new String[]{"Nguyen Tuan Anh", "11/24/2002", "2020CN2"});
        studentDatabase.put("2055010026", new String[]{"Nguyen Van Cuong", "8/5/2002", "2020CN2"});
        studentDatabase.put("2055010056", new String[]{"Nguyen Van Dang", "10/20/2002", "2020CN2"});
        studentDatabase.put("2055010038", new String[]{"Le Quang Dien", "5/14/2002", "2020CN2"});
        studentDatabase.put("2055010067", new String[]{"Tran Minh Duc", "4/19/2001", "2020CN2"});
        studentDatabase.put("2055010048", new String[]{"Duong Canh Duong", "6/16/2002", "2020CN2"});
        studentDatabase.put("2055010050", new String[]{"Nguyen Khanh Duong", "8/9/2002", "2020CN2"});
        studentDatabase.put("2055010044", new String[]{"Le Van Duy", "6/6/2002", "2020CN2"});
        studentDatabase.put("2055010068", new String[]{"Bui Thi Huong Giang", "7/23/2002", "2020CN2"});
        studentDatabase.put("2055010080", new String[]{"Vu Thi Thu Hien", "7/23/2002", "2020CN2"});
        studentDatabase.put("2055010086", new String[]{"Dao Trung Hieu", "12/13/2002", "2020CN2"});
    }

    private static boolean checkStudentId(String studentId) {
        return Pattern.matches("\\d{10}", studentId);
    }

    private static boolean checkStudent(String studentId) {
        return studentDatabase.containsKey(studentId);
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server ok...");

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
                out.println("Ma sinh vien khong duoc de trong");
            } else {
                try {
                    Long.parseLong(studentId);
                    if (!checkStudentId(studentId)) {
                        out.println("Ma sinh vien phai co 10 so");
                    } else {
                        if (checkStudent(studentId)) {
                            String[] studentInfo = studentDatabase.get(studentId);
                            out.println("Thong tin sinh vien: "+"Ma sinh vien "+studentId +", /n Ho va ten: "+ studentInfo[0] + ", /n Ngay sinh: " + studentInfo[1] + ", Lop: " + studentInfo[2]);
                        } else {
                            out.println("Khong tim thay thong tin cua ma sinh vien: " + studentId);
                        }
                    }
                } catch (NumberFormatException ex) {
                    out.println("Ma sinh vien khong hop le. Vui long chi nhap so.");
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


