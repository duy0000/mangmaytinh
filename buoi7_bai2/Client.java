package buoi7_bai2;

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
    private static final int SERVER_PORT = 2001;

    private JFrame frame;
    private JTextField messageField;
    private JButton sendButton;
    private JTextArea chatArea;
    private Socket socket;
    private PrintWriter out;
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
        frame.getContentPane().add(new JScrollPane(chatArea));
        frame.getContentPane().add(messageField);
        frame.getContentPane().add(emojiList);
        frame.getContentPane().add(sendButton);
        frame.setLayout(new FlowLayout());
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private void connectToServer() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
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
