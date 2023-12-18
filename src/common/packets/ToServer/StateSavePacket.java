package common.packets.ToServer;

import common.packets.Packet;
import common.packets.PacketType;

public class StateSavePacket extends Packet {
    public StateSavePacket() {
        super(PacketType.STATE_SAVE);
    }
}
