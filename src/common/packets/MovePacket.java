package common.packets;

import common.GameContext;
import common.PacketTypes;

public class MovePacket extends Packet{
    private int xCoord;
    public MovePacket(int senderId, int xCoord){
        super(PacketTypes.MOVE, senderId);
        this.xCoord = xCoord;
    }
    @Override
    public void handle(GameContext context) {
        context.processMovePacket(this);
    }
    public int getxCoord() {
        return xCoord;
    }
}
