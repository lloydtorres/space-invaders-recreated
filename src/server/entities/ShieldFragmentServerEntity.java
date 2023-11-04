package server.entities;

import common.EntityType;

public class ShieldFragmentServerEntity extends ServerEntity{
    public ShieldFragmentServerEntity(float X, float Y) {
        super(EntityType.SHIELD, X, Y, 5, 5, 0, 0);
    }
}
