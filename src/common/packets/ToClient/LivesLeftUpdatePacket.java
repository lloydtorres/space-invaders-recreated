package common.packets.ToClient;

import common.packets.Packet;
import common.packets.PacketType;

public class LivesLeftUpdatePacket extends Packet {
    private int newLivesLeft;

    public LivesLeftUpdatePacket(int senderId, int newLivesLeft) {
        super(PacketType.LIVES_LEFT_UPDATE, senderId);
        this.newLivesLeft = newLivesLeft;
    }

    public int getNewLivesLeft() {
        return newLivesLeft;
    }
}
