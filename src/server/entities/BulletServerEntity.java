package server.entities;

public class BulletServerEntity extends ServerEntity {
    private BulletSender bulletSender;
    public BulletServerEntity(float X, float Y, BulletSender bulletSender){
        super(X, Y, 2, 14, 0, 10);
        this.bulletSender = bulletSender;
    }

    public BulletSender getBulletSender() {
        return bulletSender;
    }
}
