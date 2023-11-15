package common.packets.ToClient;

import common.packets.Packet;
import common.packets.PacketType;

public class IdPacket extends Packet {
    private int newId;

    public IdPacket() {
        super(PacketType.ID);
    }

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }
}
