package common.packets.ToClient;

import common.EntityType;
import common.packets.Packet;
import common.packets.PacketType;

public class EntityRemovePacket extends Packet {
    private EntityType entityType;
    private int entityId;

    public EntityRemovePacket() {
        super(PacketType.ENTITY_REMOVE);
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }
}
