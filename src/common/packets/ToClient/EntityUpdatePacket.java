package common.packets.ToClient;

import common.packets.Packet;
import common.packets.PacketType;
import common.EntityType;

public class EntityUpdatePacket extends Packet {
    private float newX, newY;
    private EntityType entityType;
    private int entityId;

    public EntityUpdatePacket() {
        super(PacketType.ENTITY_UPDATE);
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public float getNewX() {
        return newX;
    }

    public void setNewX(float newX) {
        this.newX = newX;
    }


    public float getNewY() {
        return newY;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }
}
