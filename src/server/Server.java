package server;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import common.*;
import common.packets.IPacketFactory;
import common.packets.PacketFactory;
import common.packets.ToClient.*;
import common.packets.Packet;
import common.packets.builders.*;
import server.entities.Entity;
import server.entities.ServerEntity;

// main server class. waits for client connections, assigns them their unique ids
// creates client handlers for each client
// game loop should be handled here
public class Server {
    private final ServerSocket serverSocket;
    private final Map<Integer, ServerPlayer> connectedPlayers;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        connectedPlayers = new ConcurrentHashMap<>();
    }

    public void addServerPlayer(int id, ServerPlayer serverPlayer) {
        connectedPlayers.put(id, serverPlayer);
    }

    public void removeServerPlayer(int id) {
        connectedPlayers.remove(id);
        GameLoop gameLoop = GameLoop.getInstance();
        gameLoop.getState().removeEntity(id, EntityType.PLAYER);
        if (connectedPlayers.size() == 0) {
            gameLoop.setGameRunning(false);
        }
    }

    public void start() {
        appendLineToLog("Server is running on port " + serverSocket.getLocalPort() + ". Waiting for clients...");
        GameLoop gameLoop = GameLoop.getInstance();
        IPacketFactory packetFactory = new PacketFactory();
        gameLoop.getState().addObserver(new StatePacketGenerator(this, packetFactory));

        ServerGameContext context = new ServerGameContext(this, gameLoop.getState());
        PacketHandler packetHandler = new PacketHandler(context);
        Thread packetHandlerThread = new Thread(packetHandler);
        packetHandlerThread.start();
        Thread gameLoopThread = new Thread(gameLoop);
        gameLoopThread.start();
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                // Handle the client in a separate thread
                int playerId = gameLoop.getState().addPlayerEntity();
                Thread clientHandler = new Thread(new ClientHandler(clientSocket, playerId, packetHandler, this));
                clientHandler.start();
                gameLoop.setGameRunning(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendPacketToPlayer(Packet packet, int playerId) {
        connectedPlayers.get(playerId).getClientHandler().enqueuePacket(packet);
    }

    public void broadcastMessage(String message) {
        MessagePacket messagePacket = new MessagePacketBuilder()
                .setSenderId(Configuration.SERVER_ID)
                .setMessage(message)
                .getResult();
        broadcastPacket(messagePacket);
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
            PlayerAddPacket playerAddPacket = new PlayerAddPacketBuilder()
                    .setSenderId(Configuration.SERVER_ID)
                    .setPlayerId(serverPlayer.getId())
                    .setPlayerName(serverPlayer.getPlayerName())
                    .getResult();
            clientHandler.enqueuePacket(playerAddPacket);
        }
    }

    public void sendStateToPlayer(int playerId) {
        // send all the entity data, score and lives left to a player. This should only be called when a new player joins
        // after this, state update should be sent only by observing the state
        GameLoop gameLoop = GameLoop.getInstance();
        System.out.println("Singleton's hashcode: " + gameLoop.hashCode());
        int score = gameLoop.getState().getScore();
        int livesLeft = gameLoop.getState().getLivesLeft();
        Map<Integer, Entity> entities = gameLoop.getState().getAllEntities();
        ClientHandler clientHandler = connectedPlayers.get(playerId).getClientHandler();

        ScoreUpdatePacket scoreUpdatePacket = new ScoreUpdatePacketBuilder()
                .setSenderId(Configuration.SERVER_ID)
                .setNewScore(score)
                .getResult();
        clientHandler.enqueuePacket(scoreUpdatePacket);

        LivesLeftUpdatePacket livesLeftUpdatePacket = new LivesLeftUpdatePacketBuilder()
                .setSenderId(Configuration.SERVER_ID)
                .setNewLivesLeft(livesLeft)
                .getResult();
        clientHandler.enqueuePacket(livesLeftUpdatePacket);

        for (Entity entity : entities.values()) {
            EntityUpdatePacket entityUpdatePacket = new EntityUpdatePacketBuilder()
                    .setSenderId(Configuration.SERVER_ID)
                    .setEntityId(entity.getId())
                    .setEntityType(entity.getEntityType())
                    .setNewX(entity.getX())
                    .setNewY(entity.getY())
                    .getResult();
            clientHandler.enqueuePacket(entityUpdatePacket);
        }

    }

    public static void main(String[] args) {
        int port = 12345;
        try {
            Server server = new Server(port);
            server.start();
        } catch (IOException e) {
            System.out.println("Error! Could not start the server.");
        }
    }
}
