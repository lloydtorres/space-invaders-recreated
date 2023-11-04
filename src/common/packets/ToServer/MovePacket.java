package common.packets.ToServer;

import common.GameContext;
import common.MoveDirection;
import common.PacketTypes;
import common.packets.Packet;
import common.packets.PacketType;

public class MovePacket extends Packet {
    private MoveDirection moveDirection;
    public MovePacket(int senderId, MoveDirection moveDirection){
        super(PacketType.MOVE, senderId);
        this.moveDirection = moveDirection;
    }
    public MoveDirection getMoveDirection() {
        return moveDirection;
    }
}
