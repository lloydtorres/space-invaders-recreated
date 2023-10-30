package client;

import server.ClientHandler;

public class ClientPlayer {
    private int id;
    private String name;
    public ClientPlayer(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
