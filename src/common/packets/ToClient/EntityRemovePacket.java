package common.packets.ToClient;

import common.packets.Packet;
import common.packets.PacketType;
import common.EntityType;

public class EntityRemovePacket extends Packet {
    private EntityType entityType;
    private int entityId;
    public EntityRemovePacket(int senderId, EntityType entityType, int entityId) {
        super(PacketType.ENTITY_REMOVE, senderId);
        this.entityId = entityId;
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
