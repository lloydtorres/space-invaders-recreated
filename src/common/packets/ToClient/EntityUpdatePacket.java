package common.packets.ToClient;

import common.packets.Packet;
import common.packets.PacketType;
import common.EntityType;

public class EntityUpdatePacket extends Packet {
    private float newX, newY;
    private EntityType entityType;
    private int entityId;
    public EntityUpdatePacket(int senderId, EntityType entityType, int entityId, float newX, float newY) {
        super(PacketType.ENTITY_UPDATE, senderId);
        this.entityType = entityType;
        this.entityId = entityId;
        this.newX = newX;
        this.newY = newY;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public float getNewX() {
        return newX;
    }

    public float getNewY() {
        return newY;
    }
}
