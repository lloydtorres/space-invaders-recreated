package common.packets;

import common.GameContext;
import common.PacketTypes;

public class ShotPacket extends Packet {

    public ShotPacket(int senderId) {
        super(PacketTypes.SHOT, senderId);
    }

    @Override
    public void handle(GameContext context) {
        context.processShotPacket(this);
    }
}
