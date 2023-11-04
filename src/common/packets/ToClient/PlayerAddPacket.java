package common.packets.ToClient;

import common.GameContext;
import common.PacketTypes;
import common.packets.Packet;

public class PlayerAddPacket extends Packet {
    private String playerName;
    private int playerId;
    public PlayerAddPacket(int senderId, String playerName, int playerId) {
        super(PacketTypes.PLAYER_ADD, senderId);
        this.playerName = playerName;
        this.playerId = playerId;
    }

    @Override
    public void handle(GameContext context) {
        context.processPlayerAddPacket(this);
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerId() {
        return playerId;
    }
}
