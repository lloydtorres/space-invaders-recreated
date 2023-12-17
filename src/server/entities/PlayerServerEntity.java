package server.entities;

import common.EntityType;
import server.visitors.Visitor;

public class PlayerServerEntity extends ServerEntity {
    public PlayerServerEntity(float X, float Y){
        super(EntityType.PLAYER, X, Y);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitPlayer(this);
    }
}
