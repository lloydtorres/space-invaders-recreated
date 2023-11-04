package common.packets.ToServer;

import common.GameContext;
import common.PacketTypes;
import common.packets.Packet;
import common.packets.PacketType;

public class ShootPacket extends Packet {
    public ShootPacket(int senderId) {
        super(PacketType.SHOOT, senderId);
    }
}
