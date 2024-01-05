package buoi8.buoi8_bai1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class Client {
    private static final String SERVER_IP = "192.168.1.127";
    private static final int SERVER_PORT = 2001;

    private JFrame frame;
    private JTextField messageField;
    private JButton sendButton;
    private JButton sendFileButton;  // Thêm nút để gửi tệp tin
    private JTextArea chatArea;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JComboBox<String> emojiList;

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
        getClientName();
        initializeGUI();
        connectToServer();
    }

    private void getClientName() {
        clientName = JOptionPane.showInputDialog("Enter your name:");
        if (clientName == null || clientName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid name. Exiting...");
            System.exit(0);
        }
    }

    private void initializeGUI() {
        frame = new JFrame("Group Chat");
        messageField = new JTextField(20);
        sendButton = new JButton("Send");
        sendFileButton = new JButton("Send File");  // Thêm nút để gửi tệp tin
        chatArea = new JTextArea(10, 30);

        // Thêm danh sách lựa chọn biểu tượng vào JComboBox
        emojiList = new JComboBox<>(new String[]{"\uD83D\uDE0A", "\u2764\uFE0F", "\uD83C\uDF89", "\uD83D\uDC4D"});

        emojiList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Khi chọn một biểu tượng, thêm nó vào cuối tin nhắn
                messageField.setText(messageField.getText() + " " + emojiList.getSelectedItem());
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageToServer(messageField.getText());
            }
        });

        sendFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseAndSendFile();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(chatArea));
        frame.getContentPane().add(messageField);
        frame.getContentPane().add(emojiList);
        frame.getContentPane().add(sendButton);
        frame.getContentPane().add(sendFileButton);
        frame.setLayout(new FlowLayout());
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private void connectToServer() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Gửi tên người dùng đến server khi kết nối
        out.println(clientName);

        // Bắt đầu một luồng để lắng nghe tin nhắn từ server
        new Thread(() -> {
            try {
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
        messageField.setText("");  // Xóa nội dung ô nhập liệu sau khi gửi tin nhắn
    }

    private void chooseAndSendFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            sendFileToServer(selectedFile);
        }
    }

    private void sendFileToServer(File file) {
        try {
            out.println("/sendfile");
            out.println(file.getName());  // Gửi tên tệp tin trước
    
            // Đọc nội dung của tệp tin và gửi đến server
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    out.println(line);
                }
            }
    
            out.println("/endfile");  // Kết thúc tệp tin
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private void displayMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }
}
