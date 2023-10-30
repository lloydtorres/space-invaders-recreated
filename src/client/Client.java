package client;
import common.PacketHandler;

import java.io.*;
import java.net.Socket;

public class Client {
    private ConnectionFrame connectionFrame;
    private SpaceInvadersFrame spaceInvadersFrame;
    private ServerHandler serverHandler;
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
        ClientGameContext context = new ClientGameContext(spaceInvadersFrame);
        PacketHandler packetHandler = new PacketHandler(context);
        Thread packetHandlerThread = new Thread(packetHandler);
        packetHandlerThread.start();
        serverHandler = new ServerHandler(serverSocket, packetHandler);
        serverHandler.sendPlayerName(playerName);
        spaceInvadersFrame.appendToLog("CONNECTED! Requesting for id assignment...");
        Thread serverCommunicatorThread = new Thread(serverHandler);
        serverCommunicatorThread.start();
        serverHandler.sendIdRequest();
        int id = serverHandler.receiveId();
        if(id == -10){
            spaceInvadersFrame.appendToLog("SERVER ERROR! Did not receive an id");
            spaceInvadersFrame.setVisible(false);
            connectionFrame.setVisible(true);
            return;
        }
        spaceInvadersFrame.appendToLog("SUCCESS! Received id is " + id);
    }
    private Socket connectToServer(String serverAddress, int serverPort){
        Socket socket;
        try{
            socket = new Socket(serverAddress, serverPort);
            return socket;
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }


}
