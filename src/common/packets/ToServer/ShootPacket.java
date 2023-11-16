package common.packets.ToServer;

import common.packets.Packet;
import common.packets.PacketType;

public class ShootPacket extends Packet {
    public ShootPacket() {
        super(PacketType.SHOOT);
    }
}
