package common.packets;

import common.GameContext;
import common.PacketTypes;

public class IdPacket extends Packet{
    private int newId;
    public IdPacket(int senderId, int newId){
        super(PacketTypes.ID, senderId);
        this.newId = newId;
    }

    public int getNewId() {
        return newId;
    }
    @Override
    public void handle(GameContext context){
        context.processIdPacket(this);
    }
}
