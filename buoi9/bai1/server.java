package buoi9.bai1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class server {
    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        Map<String, String> USER_CREDENTIALS = new HashMap<>();
        USER_CREDENTIALS.put("admin", "admin123");

        try {
            ServerSocket serverSocket = new ServerSocket(18);
            System.out.println("Server listening on port 8888...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection from " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, USER_CREDENTIALS);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;
        private Map<String, String> userCredentials;

        public ClientHandler(Socket clientSocket, Map<String, String> userCredentials) {
            try {
                this.clientSocket = clientSocket;
                this.dataInputStream = new DataInputStream(clientSocket.getInputStream());
                this.dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                this.userCredentials = userCredentials;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                if (!authenticate()) {
                    return;
                }

                sendMenu();

                boolean shouldExit = false;
                while (!shouldExit) {
                    String choice = dataInputStream.readUTF();

                    switch (choice) {
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
                            disconnectClient();
                            shouldExit = true;
                            break;
                        default:
                            dataOutputStream.writeUTF("Invalid choice. Try again.");
                            break;
                    }

                    sendMenu();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (clientSocket != null) {
                        clientSocket.close();
                    }
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean authenticate() throws IOException {
            try {
                dataOutputStream.writeUTF("Enter username: ");
                String username = dataInputStream.readUTF();
                dataOutputStream.writeUTF("Enter password: ");
                String password = dataInputStream.readUTF();

                if (authenticateUser(username, password)) {
                    dataOutputStream.writeUTF("Authentication successful!");
                    return true;
                } else {
                    dataOutputStream.writeUTF("Authentication failed. Disconnecting...");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        private void sendMenu() throws IOException {
            dataOutputStream.writeUTF("\nMenu:");
            dataOutputStream.writeUTF("1. List files");
            dataOutputStream.writeUTF("2. Download file");
            dataOutputStream.writeUTF("3. Upload file");
            dataOutputStream.writeUTF("4. Create directory");
            dataOutputStream.writeUTF("5. Disconnect");
            dataOutputStream.writeUTF("Enter your choice: ");
            dataOutputStream.flush();
        }

        private void listFiles() throws IOException {
            File currentDirectory = new File(".");
            File[] files = currentDirectory.listFiles();
            StringBuilder fileList = new StringBuilder();

            for (File file : files) {
                fileList.append(file.getName()).append("\n");
            }

            dataOutputStream.writeUTF(fileList.toString());
        }

        private void downloadFile() throws IOException {
            dataOutputStream.writeUTF("Enter the filename to download: ");
            String filename = dataInputStream.readUTF();

            File fileToDownload = new File(filename);

            if (fileToDownload.exists() && fileToDownload.isFile()) {
                try (FileInputStream fileInputStream = new FileInputStream(fileToDownload);
                     BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                        dataOutputStream.write(buffer, 0, bytesRead);
                    }

                    dataOutputStream.writeUTF("DONE"); // Gửi thông báo hoàn tất

                } catch (FileNotFoundException e) {
                    dataOutputStream.writeUTF("File not found");
                }
            } else {
                dataOutputStream.writeUTF("Invalid filename or file not found");
            }
        }

        private void uploadFile() throws IOException {
            dataOutputStream.writeUTF("Enter the filename to upload: ");
            String filename = dataInputStream.readUTF();

            try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = clientSocket.getInputStream().read(buffer)) != -1) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void createDirectory() throws IOException {
            dataOutputStream.writeUTF("Enter the directory name to create: ");
            String dirname = dataInputStream.readUTF();

            File newDirectory = new File(dirname);

            if (newDirectory.mkdir()) {
                dataOutputStream.writeUTF("Directory created successfully");
            } else {
                dataOutputStream.writeUTF("Failed to create directory");
            }
        }

        private void disconnectClient() throws IOException {
            dataOutputStream.writeUTF("Disconnecting...");
        }

        private boolean authenticateUser(String username, String password) {
            if (userCredentials.containsKey(username)) {
                String storedPassword = userCredentials.get(username);
                return storedPassword != null && storedPassword.equals(password);
            }
            return false;
        }
    }
}
