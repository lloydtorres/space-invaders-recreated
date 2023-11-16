package common.packets.builders;

import common.packets.ToClient.IdPacket;

public class IdPacketBuilder implements PacketBuilder {
    private IdPacket idPacket;

    public IdPacketBuilder() {
        this.reset();
    }

    @Override
    public IdPacketBuilder setSenderId(int senderId) {
        idPacket.setSenderId(senderId);
        return this;
    }

    @Override
    public IdPacketBuilder reset() {
        idPacket = new IdPacket();
        return this;
    }

    public IdPacketBuilder setNewId(int newId) {
        idPacket.setNewId(newId);
        return this;
    }

    public IdPacket getResult() {
        return idPacket;
    }
}
