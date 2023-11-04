package common.packets.ToClient;

import common.packets.Packet;
import common.packets.PacketType;

public class ScoreUpdatePacket extends Packet {
    private int newScore;
    public ScoreUpdatePacket(int senderId, int newScore) {
        super(PacketType.SCORE_UPDATE, senderId);
        this.newScore = newScore;
    }

    public int getNewScore() {
        return newScore;
    }
}
