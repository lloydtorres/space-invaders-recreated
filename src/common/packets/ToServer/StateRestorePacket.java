package common.packets.ToServer;

import common.packets.Packet;
import common.packets.PacketType;

public class StateRestorePacket extends Packet {
    public StateRestorePacket() {
        super(PacketType.STATE_RESTORE);
    }
}
