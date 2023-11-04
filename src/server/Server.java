package server;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import common.*;
import common.packets.ToClient.MessagePacket;
import common.packets.ToServer.MovePacket;
import common.packets.Packet;
import common.packets.ToClient.PlayerAddPacket;

// main server class. waits for client connections, assigns them their unique ids
// creates client handlers for each client
// game loop should be handled here
public class Server {
    private ServerSocket serverSocket;
    private Map<Integer, ServerPlayer> connectedPlayers;
    private int IDCounter = 0;
    private GameLoop gameLoop;

    public Server(int port) throws IOException{
            serverSocket = new ServerSocket(port);
            connectedPlayers = new ConcurrentHashMap<>();
    }

    public void addServerPlayer(int id, ServerPlayer serverPlayer) {
        connectedPlayers.put(id, serverPlayer);
    }

    public void removeServerPlayer(int id) {
        connectedPlayers.remove(id);
        if(connectedPlayers.size() == 0){
            gameLoop.setGameRunning(false);
        }
    }

    public void start() {
        appendLineToLog("Server is running on port " + serverSocket.getLocalPort() + ". Waiting for clients...");
        ServerGameContext context = new ServerGameContext(this);
        PacketHandler packetHandler = new PacketHandler(context);
        Thread packetHandlerThread = new Thread(packetHandler);
        packetHandlerThread.start();
        gameLoop = new GameLoop(30);
        Thread gameLoopThread = new Thread(gameLoop);
        gameLoopThread.start();
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                // Handle the client in a separate thread
                Thread clientHandler = new Thread(new ClientHandler(clientSocket, IDCounter, packetHandler, this));
                clientHandler.start();
                gameLoop.getState().addPlayerEntity(IDCounter);
                gameLoop.setGameRunning(true);
                IDCounter++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendPacketToPlayer(Packet packet, int playerId) {
        connectedPlayers.get(playerId).getClientHandler().enqueuePacket(packet);
    }

    public void broadcastMessage(String message) {
        broadcastPacket(new MessagePacket(Configuration.SERVER_ID, message));
    }

    public void broadcastPacket(Packet packet) {
        for (ServerPlayer serverPlayer : connectedPlayers.values()) {
            serverPlayer.getClientHandler().enqueuePacket(packet);
        }
    }

    public void appendLineToLog(String logEntry) {
        System.out.println("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + logEntry);
    }

    public void sendAllPlayersToPlayer(int playerId) {
        ClientHandler clientHandler = connectedPlayers.get(playerId).getClientHandler();
        for (ServerPlayer serverPlayer : connectedPlayers.values()) {
            clientHandler.enqueuePacket(new PlayerAddPacket(Configuration.SERVER_ID, serverPlayer.getPlayerName(), serverPlayer.getId()));
        }
    }
    public void sendStateToPlayer(int playerId){
        // send all of the entity data, score and lives left to a player that has connected to a running game
        // after this, state update should be sent only by observing the state
        // this requires more packet types
    }

    public static void main(String[] args) {
        int port = 12345;
        try {
            Server server = new Server(port);
            server.start();
        }catch (IOException e){
            System.out.println("Error! Could not start the server.");
        }
    }
}
