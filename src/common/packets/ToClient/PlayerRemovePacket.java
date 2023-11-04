package common.packets.ToClient;

import common.GameContext;
import common.PacketTypes;
import common.packets.Packet;
import common.packets.PacketType;

public class PlayerRemovePacket extends Packet {
    private int playerId;
    public PlayerRemovePacket(int senderId, int playerId) {
        super(PacketType.PLAYER_REMOVE, senderId);
        this.playerId = playerId;
    }
    public int getPlayerId() {
        return playerId;
    }
}
