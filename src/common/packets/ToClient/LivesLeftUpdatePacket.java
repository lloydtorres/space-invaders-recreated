package common.packets.ToClient;

import common.packets.Packet;
import common.packets.PacketType;

public class LivesLeftUpdatePacket extends Packet {
    private int newLivesLeft;

    public LivesLeftUpdatePacket() {
        super(PacketType.LIVES_LEFT_UPDATE);
    }

    public int getNewLivesLeft() {
        return newLivesLeft;
    }

    public void setNewLivesLeft(int newLivesLeft) {
        this.newLivesLeft = newLivesLeft;
    }
}
