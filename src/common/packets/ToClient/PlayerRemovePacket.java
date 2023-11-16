package common.packets.ToClient;

import common.packets.Packet;
import common.packets.PacketType;

public class PlayerRemovePacket extends Packet {
    private int playerId;

    public PlayerRemovePacket() {
        super(PacketType.PLAYER_REMOVE);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
