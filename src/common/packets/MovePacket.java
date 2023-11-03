package common.packets;

import common.GameContext;
import common.PacketTypes;

public class MovePacket extends Packet{
    private int x;
    public MovePacket(int senderId, int x){
        super(PacketTypes.MOVE, senderId);
        this.x = x;
    }
    @Override
    public void handle(GameContext context) {
        context.processMovePacket(this);
    }
    public int getX() {
        return x;
    }
}
