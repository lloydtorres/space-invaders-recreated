package common.packets.builders;

import common.EntityType;
import common.packets.ToClient.EntityUpdatePacket;

public class EntityUpdatePacketBuilder implements PacketBuilder {
    private EntityUpdatePacket entityUpdatePacket;

    public EntityUpdatePacketBuilder() {
        this.reset();
    }

    @Override
    public EntityUpdatePacketBuilder setSenderId(int senderId) {
        entityUpdatePacket.setSenderId(senderId);
        return this;
    }

    @Override
    public EntityUpdatePacketBuilder reset() {
        entityUpdatePacket = new EntityUpdatePacket();
        return this;
    }

    public EntityUpdatePacketBuilder setEntityId(int entityId) {
        entityUpdatePacket.setEntityId(entityId);
        return this;
    }

    public EntityUpdatePacketBuilder setEntityType(EntityType entityType) {
        entityUpdatePacket.setEntityType(entityType);
        return this;
    }

    public EntityUpdatePacketBuilder setNewX(float newX) {
        entityUpdatePacket.setNewX(newX);
        return this;
    }

    public EntityUpdatePacketBuilder setNewY(float newY) {
        entityUpdatePacket.setNewY(newY);
        return this;
    }

    public EntityUpdatePacket getResult() {
        return entityUpdatePacket;
    }
}
