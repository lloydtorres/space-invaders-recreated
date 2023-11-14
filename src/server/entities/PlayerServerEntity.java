package server.entities;

import common.EntityType;

public class PlayerServerEntity extends ServerEntity {
    public PlayerServerEntity(float X, float Y){
        super(EntityType.PLAYER, X, Y, 38, 25, 5, 0);
    }
}
