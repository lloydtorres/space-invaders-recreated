package server.entities;

public class EnemyEntity extends Entity {
    private int pointWorth;
    public EnemyEntity(int id, float X, float Y, int pointWorth){
        super(id, X, Y, 37, 24);
        this.pointWorth = pointWorth;
    }

    public void setPointWorth(int pointWorth) {
        this.pointWorth = pointWorth;
    }

    public int getPointWorth() {
        return pointWorth;
    }
}
