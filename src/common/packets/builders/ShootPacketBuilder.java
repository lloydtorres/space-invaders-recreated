package common.packets.builders;

import common.packets.ToServer.ShootPacket;

public class ShootPacketBuilder implements PacketBuilder {
    private ShootPacket shootPacket;

    public ShootPacketBuilder() {
        this.reset();
    }

    @Override
    public ShootPacketBuilder setSenderId(int senderId) {
        shootPacket.setSenderId(senderId);
        return this;
    }

    @Override
    public ShootPacketBuilder reset() {
        shootPacket = new ShootPacket();
        return this;
    }

    public ShootPacket getResult() {
        return shootPacket;
    }
}
