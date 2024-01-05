package buoi7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "192.168.1.127";
    private static final int SERVER_PORT = 2002;

    private JFrame frame;
    private JTextField messageField;
    private JButton sendButton;
    private JTextArea chatArea;
    private Socket socket;
    private PrintWriter out;

    // Thêm biến để lưu tên người dùng
    private String clientName;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Client().startClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void startClient() throws IOException {
        getClientName(); // Thêm hàm để nhập tên người dùng
        initializeGUI();
        connectToServer();
    }

    // Hàm để hiển thị hộp thoại nhập tên người dùng
    private void getClientName() {
        clientName = JOptionPane.showInputDialog("Enter your name:");
        if (clientName == null || clientName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid name. Exiting...");
            System.exit(0);
        }
    }

    private void initializeGUI() {
        frame = new JFrame("Đoạn Chat Nhóm");
        messageField = new JTextField(20);
        sendButton = new JButton("Send");
        chatArea = new JTextArea(10, 30);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        sendMessageToServer(messageField.getText());
                        return null;
                    }
                }.execute();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(messageField);
        frame.getContentPane().add(sendButton);
        frame.getContentPane().add(new JScrollPane(chatArea));
        frame.setLayout(new FlowLayout());
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private void connectToServer() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        out = new PrintWriter(socket.getOutputStream(), true);

        // Gửi tên người dùng đến server khi kết nối
        out.println(clientName);

        new Thread(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = in.readLine()) != null) {
                    displayMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendMessageToServer(String message) {
        out.println(message);
    }

    private void displayMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }
}
