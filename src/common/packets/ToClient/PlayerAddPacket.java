package common.packets.ToClient;

import common.GameContext;
import common.PacketTypes;
import common.packets.Packet;
import common.packets.PacketType;

public class PlayerAddPacket extends Packet {
    private String playerName;
    private int playerId;
    public PlayerAddPacket(int senderId, String playerName, int playerId) {
        super(PacketType.PLAYER_ADD, senderId);
        this.playerName = playerName;
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerId() {
        return playerId;
    }
}
