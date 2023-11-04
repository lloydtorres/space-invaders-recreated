package server;

import common.EntityType;
import server.entities.ServerEntity;

public class EntityUpdateEvent extends GameStateEvent{
    private ServerEntity entity;
    private boolean isRemoval;
    public EntityUpdateEvent(ServerEntity entity, boolean isRemoval) {
        super(EventType.ENTITY_UPDATE);
        this.entity = entity;
        this.isRemoval = isRemoval;
    }

    public ServerEntity getEntity() {
        return entity;
    }

    public boolean isRemoval() {
        return isRemoval;
    }
}
