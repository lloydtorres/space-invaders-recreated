package client;
import common.PacketHandler;

import java.io.*;
import java.net.Socket;

public class Client {
    private ConnectionFrame connectionFrame;
    private SpaceInvadersFrame spaceInvadersFrame;
    private ServerHandler serverHandler;
    private ClientPlayer thisPlayer;
    public Client(){
        connectionFrame = new ConnectionFrame("Space Invaders MP", this);
        spaceInvadersFrame = new SpaceInvadersFrame("Space Invaders MP");
        start();
    }
    private void start(){
        connectionFrame.setVisible(true);
    }
    public static void main(String[] args) {
        new Client();
    }

    public void handleConnectPress(String serverAddress, int serverPort, String playerName){
        connectionFrame.setVisible(false);
        spaceInvadersFrame.setVisible(true);
        spaceInvadersFrame.appendToLog("Connecting to " + serverAddress + ":" + serverPort + " as <" + playerName + ">");
        Socket serverSocket = connectToServer(serverAddress, serverPort);
        if(serverSocket == null) {
            spaceInvadersFrame.appendToLog("Connection failed!");
            spaceInvadersFrame.setVisible(false);
            connectionFrame.setVisible(true);
            return;
        }
        thisPlayer = new ClientPlayer();
        thisPlayer.setName(playerName);
        ClientGameContext context = new ClientGameContext(spaceInvadersFrame, thisPlayer);
        PacketHandler packetHandler = new PacketHandler(context);
        Thread packetHandlerThread = new Thread(packetHandler);
        packetHandlerThread.start();
        serverHandler = new ServerHandler(serverSocket, packetHandler);
        serverHandler.sendPlayerName(playerName);
        Thread serverHandlerThread = new Thread(serverHandler);
        serverHandlerThread.start();
    }
    private Socket connectToServer(String serverAddress, int serverPort){
        try{
            return new Socket(serverAddress, serverPort);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }


}
