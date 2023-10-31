package server;

import common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ObjectInputStream clientIn;
    private ObjectOutputStream clientOut;
    private int playerId;
    private final BlockingQueue<Packet> outgoingPacketQueue = new LinkedBlockingQueue<>();
    private PacketHandler packetHandler;
    private Server server;
    private Thread senderThread, receiverThread;
    private ServerPlayer serverPlayer;
    public ClientHandler(Socket socket, int playerId, PacketHandler packetHandler, Server server) {
        clientSocket = socket;
        this.playerId = playerId;
        this.packetHandler = packetHandler;
        this.server = server;
        try {
            clientIn = new ObjectInputStream(clientSocket.getInputStream());
            clientOut = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendPacket(Packet packet) {
        try {
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

    private void disconnectClient() {
        // Cleanup actions for client disconnection
        try {
            // Close the client's input and output streams
            clientIn.close();
            clientOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the client socket
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the client handler from the connectedPlayers map
        server.removeServerPlayer(playerId);

        // Interrupt the sender and receiver threads to stop them
        senderThread.interrupt();
        receiverThread.interrupt();
        server.broadcastMessage("Player <" + serverPlayer.getPlayerName() + "> disconnected from the server!");
        server.appendLineToLog("Client disconnected: " + clientSocket.getInetAddress() + " as <" + serverPlayer.getPlayerName() + ">");
        server.broadcastPacket(new PlayerRemovePacket(Configuration.SERVER_ID, playerId));
    }
    @Override
    public void run() {
        try {
            // Read player name sent from the client.
            String playerName = (String) clientIn.readObject();
            serverPlayer = new ServerPlayer(playerName, playerId, clientSocket, this);
            server.broadcastMessage("Player <" + playerName + "> joined the server!");
            server.appendLineToLog("New client connected: " + clientSocket.getInetAddress() + " as <" + serverPlayer.getPlayerName() + ">");
            server.broadcastPacket(new PlayerAddPacket(Configuration.SERVER_ID, playerName, playerId));
            // Put this player and its socket into the players map
            server.addServerPlayer(playerId, serverPlayer);
            enqueuePacket(new IdPacket(Configuration.SERVER_ID, playerId));
            enqueuePacket(new MessagePacket(Configuration.SERVER_ID, "Hello from the server to you using packet framework"));
            server.sendAllPlayersToPlayer(playerId);
            // start workers for receiving and sending to client
            senderThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    Packet outgoingPacket = outgoingPacketQueue.poll();
                    if (outgoingPacket != null) {
                        sendPacket(outgoingPacket);
                    }
                }
            });

            receiverThread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Packet incomingPacket = receivePacket();
                        packetHandler.dispatchPacket(incomingPacket);
                    }
                }catch (SocketException e){
                    disconnectClient();
                }
                catch (IOException | ClassNotFoundException e) {
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

        }catch (SocketException e) {
            disconnectClient();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
