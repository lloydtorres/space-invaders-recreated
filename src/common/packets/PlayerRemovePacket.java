package common;

public class PlayerRemovePacket extends Packet{
    private int playerId;
    public PlayerRemovePacket(int senderId, int playerId) {
        super(PacketTypes.PLAYER_REMOVE, senderId);
        this.playerId = playerId;
    }

    @Override
    public void handle(GameContext context) {
        context.removePlayer(this);
    }

    public int getPlayerId() {
        return playerId;
    }
}
