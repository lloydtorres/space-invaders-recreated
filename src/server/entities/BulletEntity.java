package server.entities;

public class BulletEntity extends Entity {
    private float speed;
    public BulletEntity(int id, float X, float Y, float speed){
        super(id, X, Y, 2, 14);
        this.speed = speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
}
