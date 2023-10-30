package server;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import common.*;

public class Server {
    private ServerSocket serverSocket;
    private Map<Socket, ClientHandler> connectedPlayers;
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
        ServerGameContext context = new ServerGameContext();
        PacketHandler packetHandler = new PacketHandler(context);
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                // Handle the client in a separate thread
                Thread clientHandler = new Thread(new ClientHandler(clientSocket, IDCounter, packetHandler));
                clientHandler.start();
                IDCounter++;
                broadcastMessage("New player joined!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void broadcastMessage(String message) {
        MessagePacket messagePacket = new MessagePacket(PacketTypes.MESSAGE, Configuration.SERVER_ID, message);
        for (ClientHandler clientHandler : connectedPlayers.values()) {
            clientHandler.enqueuePacket(messagePacket);
        }
    }

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
        private final BlockingQueue<Packet> outgoingPacketQueue = new LinkedBlockingQueue<>();
        private PacketHandler packetHandler;
        public ClientHandler(Socket socket, int playerId, PacketHandler packetHandler) {
            clientSocket = socket;
            this.playerId = playerId;
            this.packetHandler = packetHandler;
            try {
                clientIn = new ObjectInputStream(clientSocket.getInputStream());
                clientOut = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void sendPacket(Packet packet) {
            try {
//                clientOut.writeInt(packet.getPacketType());
//                clientOut.flush();
                clientOut.writeObject(packet);
                clientOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private Packet receivePacket() throws IOException, ClassNotFoundException {
            Packet packet;
            packet = (Packet) clientIn.readObject();
            return packet;
        }
        public void enqueuePacket(Packet packet) {
            outgoingPacketQueue.offer(packet);
        }
        @Override
        public void run() {
            try {
                // Read player name sent from the client.
                String playerName = (String) clientIn.readObject();
                Player player = new Player(playerName, playerId);
                // Put this player and its socket into the players map
                connectedPlayers.put(clientSocket, this);
                // wait for the id request from client and only then send it to them
                int request = clientIn.readInt();
                if(request == -5){
                    clientOut.writeInt(playerId);
                    clientOut.flush();
                }
                clientOut.writeObject(new MessagePacket(PacketTypes.MESSAGE, Configuration.SERVER_ID, "Hello from the server to you using packet framework"));
                clientOut.flush();
                // start workers for receiving and sending to client
                Thread senderThread = new Thread(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        Packet outgoingPacket = outgoingPacketQueue.poll();
                        if (outgoingPacket != null) {
                            sendPacket(outgoingPacket);
                        }
                    }
                });

                Thread receiverThread = new Thread(() -> {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            Packet incomingPacket = receivePacket();
                            packetHandler.dispatchPacket(incomingPacket);
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

                senderThread.start();
                receiverThread.start();
                try {
                    senderThread.join();
                    receiverThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Handle game packets and communication with the client
                // You can send and receive packets here

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
