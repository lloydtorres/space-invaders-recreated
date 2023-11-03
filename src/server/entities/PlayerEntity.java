package server.entities;

public class PlayerEntity extends Entity {
    private int points;
    private String name;
    public PlayerEntity(int id, float X, float Y, String name){
        super(id, X, Y, 38, 25);
        this.name = name;
        points = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
