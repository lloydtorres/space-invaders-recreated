package server.entities;

import common.EntityType;

public class ShieldFragmentServerEntity extends ServerEntity{
    public ShieldFragmentServerEntity(float X, float Y) {
        super(EntityType.SHIELD, X, Y, 10, 10, 0, 0);
    }
}
