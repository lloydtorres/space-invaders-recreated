package server.entities;

public class EnemyServerEntity extends ServerEntity {
    private int pointWorth;
    public EnemyServerEntity(float X, float Y, int pointWorth){
        super(X, Y, 37, 24, 18, 24);
        this.pointWorth = pointWorth;
    }

    public void setPointWorth(int pointWorth) {
        this.pointWorth = pointWorth;
    }

    public int getPointWorth() {
        return pointWorth;
    }
}
