package buoi9.bai1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client extends JFrame {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Scanner scanner;

    public client() {
        try {
            socket = new Socket("localhost", 18);
            System.out.println("Connected to server.");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            scanner = new Scanner(System.in);

            authenticate();

            SwingUtilities.invokeLater(this::createMainMenu);

        } catch (IOException e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private void authenticate() {
        try {
            JOptionPane.showMessageDialog(this, reader.readLine(), "Authentication", JOptionPane.INFORMATION_MESSAGE);

            String username = JOptionPane.showInputDialog(this, "Enter username:");
            String password = JOptionPane.showInputDialog(this, "Enter password:");

            writer.write(username + "\n");
            writer.write(password + "\n");
            writer.flush();

            String authenticationResult = reader.readLine();
            JOptionPane.showMessageDialog(this, authenticationResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        for (int i = 1; i <= 5; i++) {
            JButton button = new JButton("Option " + i);
            button.addActionListener(new OptionButtonListener(Integer.toString(i)));
            panel.add(button);
        }

        add(panel);
        setVisible(true);
    }

    private class OptionButtonListener implements ActionListener {
        private String option;

        public OptionButtonListener(String option) {
            this.option = option;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                writer.write(option + "\n");
                writer.flush();

                switch (option) {
                    case "1":
                        listFiles();
                        break;
                    case "2":
                        downloadFile();
                        break;
                    case "3":
                        uploadFile();
                        break;
                    case "4":
                        createDirectory();
                        break;
                    case "5":
                        System.out.println("Disconnecting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void listFiles() {
        try {
            JOptionPane.showMessageDialog(this, reader.readLine(), "File List", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadFile() {
        String filename = JOptionPane.showInputDialog(this, "Enter the filename to download:");
        System.out.println("Downloading file...");

        try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
             InputStream inputStream = socket.getInputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);

                if (new String(buffer, 0, bytesRead).equals("DONE")) {
                    break;
                }
            }

            System.out.println("File downloaded successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("File not found on server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadFile() {
        String filename = JOptionPane.showInputDialog(this, "Enter the filename to upload:");

        try (FileInputStream fileInputStream = new FileInputStream(filename);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             OutputStream outputStream = socket.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.write("DONE".getBytes());
            outputStream.flush();

            System.out.println("File uploaded successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDirectory() {
        String dirname = JOptionPane.showInputDialog(this, "Enter the directory name to create:");

        try {
            JOptionPane.showMessageDialog(this, reader.readLine(), "Create Directory", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new client());
    }
}
