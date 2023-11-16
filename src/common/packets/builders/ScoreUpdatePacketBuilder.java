package common.packets.builders;

import common.packets.ToClient.ScoreUpdatePacket;

public class ScoreUpdatePacketBuilder implements PacketBuilder {
    private ScoreUpdatePacket scoreUpdatePacket;

    public ScoreUpdatePacketBuilder() {
        this.reset();
    }

    @Override
    public ScoreUpdatePacketBuilder setSenderId(int senderId) {
        scoreUpdatePacket.setSenderId(senderId);
        return this;
    }

    @Override
    public ScoreUpdatePacketBuilder reset() {
        scoreUpdatePacket = new ScoreUpdatePacket();
        return this;
    }

    public ScoreUpdatePacketBuilder setNewScore(int newScore) {
        scoreUpdatePacket.setNewScore(newScore);
        return this;
    }

    public ScoreUpdatePacket getResult() {
        return scoreUpdatePacket;
    }
}
