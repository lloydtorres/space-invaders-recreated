package server.entities;

import common.EntityType;
import server.visitors.Visitor;

public class BulletServerEntity extends ServerEntity {
    private BulletSender bulletSender;
    public BulletServerEntity(float X, float Y, BulletSender bulletSender){
        super(EntityType.BULLET, X, Y, 2, 14, 0, 10);
        this.bulletSender = bulletSender;
    }

    public BulletSender getBulletSender() {
        return bulletSender;
    }

    public void acceptVisitor(Visitor visitor){
        visitor.visit(this);
    };
}
