package common.packets.builders;

import common.packets.ToServer.StateRestorePacket;

public class StateRestorePacketBuilder implements PacketBuilder {
    private StateRestorePacket stateRestorePacket;

    public StateRestorePacketBuilder() {
        this.reset();
    }

    @Override
    public StateRestorePacketBuilder setSenderId(int senderId) {
        stateRestorePacket.setSenderId(senderId);
        return this;
    }

    @Override
    public StateRestorePacketBuilder reset() {
        stateRestorePacket = new StateRestorePacket();
        return this;
    }

    public StateRestorePacket getResult() {
        return stateRestorePacket;
    }
}
