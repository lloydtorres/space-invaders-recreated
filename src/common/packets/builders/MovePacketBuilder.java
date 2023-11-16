package common.packets.builders;

import common.MoveDirection;
import common.packets.ToServer.MovePacket;

public class MovePacketBuilder implements PacketBuilder {
    private MovePacket movePacket;

    public MovePacketBuilder() {
        this.reset();
    }

    @Override
    public MovePacketBuilder setSenderId(int senderId) {
        movePacket.setSenderId(senderId);
        return this;
    }

    @Override
    public MovePacketBuilder reset() {
        movePacket = new MovePacket();
        return this;
    }

    public MovePacketBuilder setMoveDirection(MoveDirection moveDirection) {
        movePacket.setMoveDirection(moveDirection);
        return this;
    }

    public MovePacket getResult() {
        return movePacket;
    }
}
