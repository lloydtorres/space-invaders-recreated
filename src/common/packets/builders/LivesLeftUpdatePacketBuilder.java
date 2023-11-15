package common.packets.builders;

import common.packets.ToClient.LivesLeftUpdatePacket;

public class LivesLeftUpdatePacketBuilder implements PacketBuilder {
    private LivesLeftUpdatePacket livesLeftUpdatePacket;

    public LivesLeftUpdatePacketBuilder() {
        this.reset();
    }

    @Override
    public LivesLeftUpdatePacketBuilder setSenderId(int senderId) {
        livesLeftUpdatePacket.setSenderId(senderId);
        return this;
    }

    @Override
    public LivesLeftUpdatePacketBuilder reset() {
        livesLeftUpdatePacket = new LivesLeftUpdatePacket();
        return this;
    }

    public LivesLeftUpdatePacketBuilder setNewLivesLeft(int newLivesLeft) {
        livesLeftUpdatePacket.setNewLivesLeft(newLivesLeft);
        return this;
    }

    public LivesLeftUpdatePacket getResult() {
        return livesLeftUpdatePacket;
    }
}
