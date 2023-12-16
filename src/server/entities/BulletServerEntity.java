package server.entities;

import common.EntityType;
import server.visitors.Visitor;

public class BulletServerEntity extends ServerEntity {
    private BulletSender bulletSender;
    public BulletServerEntity(float X, float Y, BulletSender bulletSender){
        super(EntityType.BULLET, X, Y);
        this.bulletSender = bulletSender;
    }

    public BulletSender getBulletSender() {
        return bulletSender;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitBullet(this);
    }
}
