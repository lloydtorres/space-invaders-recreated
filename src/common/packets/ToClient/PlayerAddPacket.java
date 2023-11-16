package common.packets.ToClient;

import common.packets.Packet;
import common.packets.PacketType;

public class PlayerAddPacket extends Packet {
    private String playerName;
    private int playerId;

    public PlayerAddPacket() {
        super(PacketType.PLAYER_ADD);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
