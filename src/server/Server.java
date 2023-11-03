package server;
import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import common.*;
import common.packets.MessagePacket;
import common.packets.MovePacket;
import common.packets.Packet;
import common.packets.PlayerAddPacket;

// main server class. waits for client connections, assigns them their unique ids
// creates client handlers for each client
// game loop should be handled here
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
    public void updateServerPlayerPosition(int id, int xCoord){
        connectedPlayers.get(id).setxCoord(xCoord);
    }
    public void start() {
        appendLineToLog("Server is running on port " + serverSocket.getLocalPort() + ". Waiting for clients...");
        ServerGameContext context = new ServerGameContext(this);
        PacketHandler packetHandler = new PacketHandler(context);
        Thread packetHandlerThread = new Thread(packetHandler);
        packetHandlerThread.start();
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
    public void sendPacketToPlayer(Packet packet, int playerId){
        connectedPlayers.get(playerId).getClientHandler().enqueuePacket(packet);
    }
    public void broadcastMessage(String message) {
        broadcastPacket(new MessagePacket(Configuration.SERVER_ID, message));
    }
    public void broadcastPacket(Packet packet){
        for(ServerPlayer serverPlayer : connectedPlayers.values()){
            serverPlayer.getClientHandler().enqueuePacket(packet);
        }
    }
    public void appendLineToLog(String logEntry){
        System.out.println("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + logEntry);
    }

    public void sendAllPlayersToPlayer(int playerId){
        ClientHandler clientHandler = connectedPlayers.get(playerId).getClientHandler();
        for (ServerPlayer serverPlayer : connectedPlayers.values()){
            clientHandler.enqueuePacket(new PlayerAddPacket(Configuration.SERVER_ID, serverPlayer.getPlayerName(), serverPlayer.getId()));
            clientHandler.enqueuePacket(new MovePacket(serverPlayer.getId(), serverPlayer.getxCoord()));
        }
    }

    public static void main(String[] args) {
        int port = 12345;
        Server server = new Server(port);
        server.start();
    }


}
