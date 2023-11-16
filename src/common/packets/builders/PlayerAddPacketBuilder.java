package common.packets.builders;

import common.packets.ToClient.PlayerAddPacket;

public class PlayerAddPacketBuilder implements PacketBuilder {
    private PlayerAddPacket playerAddPacket;

    public PlayerAddPacketBuilder() {
        this.reset();
    }

    @Override
    public PlayerAddPacketBuilder setSenderId(int senderId) {
        playerAddPacket.setSenderId(senderId);
        return this;
    }

    @Override
    public PlayerAddPacketBuilder reset() {
        playerAddPacket = new PlayerAddPacket();
        return this;
    }

    public PlayerAddPacketBuilder setPlayerId(int playerId) {
        playerAddPacket.setPlayerId(playerId);
        return this;
    }

    public PlayerAddPacketBuilder setPlayerName(String playerName) {
        playerAddPacket.setPlayerName(playerName);
        return this;
    }

    public PlayerAddPacket getResult() {
        return playerAddPacket;
    }
}
