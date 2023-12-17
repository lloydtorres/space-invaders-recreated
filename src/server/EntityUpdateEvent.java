package server;

import common.EntityType;
import server.entities.Entity;
import server.entities.ServerEntity;

public class EntityUpdateEvent extends GameStateEvent{
    private Entity entity;
    private boolean isRemoval;
    public EntityUpdateEvent(Entity entity, boolean isRemoval) {
        super(EventType.ENTITY_UPDATE);
        this.entity = entity;
        this.isRemoval = isRemoval;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isRemoval() {
        return isRemoval;
    }
}
