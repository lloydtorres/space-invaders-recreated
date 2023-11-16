package common.packets.ToServer;

import common.MoveDirection;
import common.packets.Packet;
import common.packets.PacketType;

public class MovePacket extends Packet {
    private MoveDirection moveDirection;

    public MovePacket() {
        super(PacketType.MOVE);
    }

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }

    public void setMoveDirection(MoveDirection moveDirection) {
        this.moveDirection = moveDirection;
    }
}
