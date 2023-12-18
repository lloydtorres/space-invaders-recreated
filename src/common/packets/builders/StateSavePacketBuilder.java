package common.packets.builders;

import common.packets.ToServer.StateSavePacket;

public class StateSavePacketBuilder implements PacketBuilder {
    private StateSavePacket stateSavePacket;

    public StateSavePacketBuilder() {
        this.reset();
    }

    @Override
    public StateSavePacketBuilder setSenderId(int senderId) {
        stateSavePacket.setSenderId(senderId);
        return this;
    }

    @Override
    public StateSavePacketBuilder reset() {
        stateSavePacket = new StateSavePacket();
        return this;
    }

    public StateSavePacket getResult() {
        return stateSavePacket;
    }
}
