package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int serverPort = 12345;

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to the server at " + serverAddress + ":" + serverPort);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Start a thread to read and display messages from the server
            Thread readThread = new Thread(new ServerReader(in));
            readThread.start();

            // Read and send messages from the client
            Scanner scanner = new Scanner(System.in);
            String message;
            while (true) {
                message = scanner.nextLine();
                out.println(message);
            }
        } catch (IOException e) {
            System.err.println("Client exception: " + e.getMessage());
        }
    }

    private static class ServerReader implements Runnable {
        private BufferedReader in;

        public ServerReader(BufferedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println("Server says: " + message);
                }
            } catch (IOException e) {
                System.err.println("ServerReader error: " + e.getMessage());
            }
        }
    }
}
