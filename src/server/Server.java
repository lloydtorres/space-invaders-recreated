package server;
import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import common.*;

public class Server {
    private ServerSocket serverSocket;
    private Map<Integer, ServerPlayer> connectedPlayers;
    private int IDCounter = 0;
    public Server(int port) {
        connectedPlayers = new ConcurrentHashMap<>();

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addServerPlayer(int id, ServerPlayer serverPlayer){
        connectedPlayers.put(id, serverPlayer);
    }
    public void removeServerPlayer(int id){
        connectedPlayers.remove(id);
    }
    public void start() {
        appendLineToLog("Server is running on port " + serverSocket.getLocalPort() + ". Waiting for clients...");
        ServerGameContext context = new ServerGameContext();
        PacketHandler packetHandler = new PacketHandler(context);
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                // Handle the client in a separate thread
                Thread clientHandler = new Thread(new ClientHandler(clientSocket, IDCounter, packetHandler, this));
                clientHandler.start();
                IDCounter++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void broadcastMessage(String message) {
        MessagePacket messagePacket = new MessagePacket(Configuration.SERVER_ID, message);
        for (ServerPlayer serverPlayer : connectedPlayers.values()) {
            serverPlayer.getClientHandler().enqueuePacket(messagePacket);
        }
    }
    public void appendLineToLog(String logEntry){
        System.out.println("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + logEntry);
    }

    public static void main(String[] args) {
        int port = 12345;
        Server server = new Server(port);
        server.start();
    }


}
