package common.packets.builders;

import common.EntityType;
import common.packets.ToClient.EntityRemovePacket;

public class EntityRemovePacketBuilder implements PacketBuilder {
    private EntityRemovePacket entityRemovePacket;

    public EntityRemovePacketBuilder() {
        this.reset();
    }

    @Override
    public EntityRemovePacketBuilder setSenderId(int senderId) {
        entityRemovePacket.setSenderId(senderId);
        return this;
    }

    @Override
    public EntityRemovePacketBuilder reset() {
        entityRemovePacket = new EntityRemovePacket();
        return this;
    }

    public EntityRemovePacketBuilder setEntityId(int entityId) {
        entityRemovePacket.setEntityId(entityId);
        return this;
    }

    public EntityRemovePacketBuilder setEntityType(EntityType entityType) {
        entityRemovePacket.setEntityType(entityType);
        return this;
    }

    public EntityRemovePacket getResult(){
        return entityRemovePacket;
    }
}
