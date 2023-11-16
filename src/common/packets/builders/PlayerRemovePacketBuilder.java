package common.packets.builders;

import common.packets.ToClient.PlayerRemovePacket;

public class PlayerRemovePacketBuilder implements PacketBuilder {
    private PlayerRemovePacket playerRemovePacket;

    public PlayerRemovePacketBuilder() {
        this.reset();
    }

    @Override
    public PlayerRemovePacketBuilder setSenderId(int senderId) {
        return this;
    }

    @Override
    public PlayerRemovePacketBuilder reset() {
        playerRemovePacket = new PlayerRemovePacket();
        return this;
    }

    public PlayerRemovePacketBuilder setPlayerId(int playerId) {
        playerRemovePacket.setPlayerId(playerId);
        return this;
    }

    public PlayerRemovePacket getResult() {
        return playerRemovePacket;
    }
}
