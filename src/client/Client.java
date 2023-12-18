package client;

import common.PacketHandler;
import common.packets.IPacketFactory;
import common.packets.Packet;
import common.packets.PacketFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Client {
    private ConnectionFrame connectionFrame;
    private GameFrame gameFrame;
    private ServerHandler serverHandler;
    private ClientPlayer thisPlayer;
    private Map<Integer, ClientPlayer> otherPlayerList;
    private Game game;
    private SoundPlayer soundPlayer;
    public Client(){
        try{
            IPacketFactory packetFactory = new PacketFactory();
            soundPlayer = new TinySoundAdapter();
            soundPlayer.setVolume(0.5f);
            game = new Game(this, packetFactory,180, 30, soundPlayer);
            connectionFrame = new ConnectionFrame("Space Invaders MP", this);
            gameFrame = new GameFrame("Space Invaders MP", this, game);
            otherPlayerList = new ConcurrentHashMap<>();
            connectionFrame.setVisible(true);
        }catch (IOException | FontFormatException e){
            System.out.println("Could not launch the client :(");
            e.printStackTrace();
        }
    }
    public ClientPlayer getThisPlayer(){
        return thisPlayer;
    }
    public void addPlayer(int id, ClientPlayer clientPlayer){
        otherPlayerList.put(id, clientPlayer);
    }
    public void removePlayer(int id){
        otherPlayerList.remove(id);
    }
    public void sendPacket(Packet packet){
        serverHandler.enqueuePacket(packet);
    }

    // This is called when "Connect" button is pressed
    public void handleConnectPress(String serverAddress, int serverPort, String playerName){
        connectionFrame.setVisible(false);
        gameFrame.setVisible(true);
        gameFrame.appendToLog("Connecting to " + serverAddress + ":" + serverPort + " as <" + playerName + ">");
        Socket serverSocket = connectToServer(serverAddress, serverPort);
        if(serverSocket == null) {
            gameFrame.appendToLog("Connection failed!");
            gameFrame.setVisible(false);
            connectionFrame.setVisible(true);
            return;
        }
        gameFrame.start();
        thisPlayer = new ClientPlayer(playerName);

        // sending/receiving packets
        ClientGameContext context = new ClientGameContext(gameFrame, thisPlayer, this);
        PacketHandler packetHandler = new PacketHandler(context);
        Thread packetHandlerThread = new Thread(packetHandler);
        packetHandlerThread.start();

        serverHandler = new ServerHandler(serverSocket, packetHandler);
        serverHandler.sendPlayerName(playerName);
        Thread serverHandlerThread = new Thread(serverHandler);
        serverHandlerThread.start();
        Thread gameThread = new Thread(game);
        gameThread.start();
    }
    private Socket connectToServer(String serverAddress, int serverPort){
        try{
            return new Socket(serverAddress, serverPort);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
    public void exitProcedure(){
        if(soundPlayer instanceof TinySoundAdapter){
            ((TinySoundAdapter) soundPlayer).shutdown();
        }
        connectionFrame.dispose();
        gameFrame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Client();
        });
    }
}
