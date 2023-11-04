package common.packets.ToClient;

import common.GameContext;
import common.PacketTypes;
import common.packets.Packet;
import common.packets.PacketType;

public class IdPacket extends Packet {
    private int newId;
    public IdPacket(int senderId, int newId){
        super(PacketType.ID, senderId);
        this.newId = newId;
    }

    public int getNewId() {
        return newId;
    }
}
