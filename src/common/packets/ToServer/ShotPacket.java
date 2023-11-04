package common.packets.ToServer;

import common.GameContext;
import common.PacketTypes;
import common.packets.Packet;

public class ShotPacket extends Packet {

    public ShotPacket(int senderId) {
        super(PacketTypes.SHOT, senderId);
    }

    @Override
    public void handle(GameContext context) {
        context.processShotPacket(this);
    }
}
