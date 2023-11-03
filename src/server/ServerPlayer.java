package server;

import java.net.Socket;

public class ServerPlayer {
    private String playerName;
    private int id;
    private Socket socket;
    private ClientHandler clientHandler;
    private int xCoord;

    public ServerPlayer(String playerName, int id, Socket socket, ClientHandler clientHandler) {
        this.playerName = playerName;
        this.id = id;
        this.socket = socket;
        this.clientHandler = clientHandler;
        xCoord = 90;
    }

    public void setX(int x) {
        this.xCoord = x;
    }

    public int getxCoord() {
        return xCoord;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

}