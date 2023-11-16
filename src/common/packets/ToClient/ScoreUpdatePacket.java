package common.packets.ToClient;

import common.packets.Packet;
import common.packets.PacketType;

public class ScoreUpdatePacket extends Packet {
    private int newScore;
    public ScoreUpdatePacket() {
        super(PacketType.SCORE_UPDATE);
    }

    public int getNewScore() {
        return newScore;
    }

    public void setNewScore(int newScore) {
        this.newScore = newScore;
    }
}
