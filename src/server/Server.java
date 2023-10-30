package server;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import common.*;

public class Server {
    private ServerSocket serverSocket;
    private Map<Socket, Player> connectedPlayers;
    private int IDCounter = 0;
    public Server(int port) {
        connectedPlayers = new ConcurrentHashMap<>();

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Server is running. " + serverSocket.getLocalSocketAddress() + " . Waiting for clients...");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Handle the client in a separate thread
                Thread clientHandler = new Thread(new ClientHandler(clientSocket, IDCounter));
                clientHandler.start();
                IDCounter++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//    private void broadcastMessage(String message) {
//        for (Socket clientSocket : connectedPlayers.keySet()) {
//            try {
//                ObjectOutputStream clientOut = new ObjectOutputStream(clientSocket.getOutputStream());
//                MessagePacket messagePacket = new MessagePacket(message);
//                clientOut.writeObject(messagePacket);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void main(String[] args) {
        int port = 12345;
        Server server = new Server(port);
        server.start();
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ObjectInputStream clientIn;
        private ObjectOutputStream clientOut;
        private int playerId;
        public ClientHandler(Socket socket, int playerId) {
            clientSocket = socket;
            this.playerId = playerId;
            try {
                clientIn = new ObjectInputStream(clientSocket.getInputStream());
                clientOut = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // Read player name sent from the client.
                String playerName = (String) clientIn.readObject();
                Player player = new Player(playerName, playerId);
                // Put this player and its socket into the players map
                connectedPlayers.put(clientSocket, player);
                // wait for the id request from client and only then send it to them
                int request = clientIn.readInt();
                if(request == -5){
                    clientOut.writeInt(playerId);
                    clientOut.flush();
                }
                clientOut.writeObject(new MessagePacket(PacketTypes.MESSAGE, Configuration.SERVER_ID, "Hello from the server to you using packet framework"));
                clientOut.flush();


                // Handle game packets and communication with the client
                // You can send and receive packets here

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
