package server;

public class Player {
    private String playerName;
    private int id;

    public Player(String playerName, int id) {
        this.playerName = playerName;
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }
    public int getId(){
        return id;
    }
}