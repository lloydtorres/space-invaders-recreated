package common.packets.ToServer;

import common.GameContext;
import common.MoveDirection;
import common.PacketTypes;
import common.packets.Packet;

public class MovePacket extends Packet {
    private MoveDirection moveDirection;
    public MovePacket(int senderId, MoveDirection moveDirection){
        super(PacketTypes.MOVE, senderId);
        this.moveDirection = moveDirection;
    }
    @Override
    public void handle(GameContext context) {
        context.processMovePacket(this);
    }

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }
}
