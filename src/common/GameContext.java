package common;

public interface GameContext {
    void processReceivedMessage(MessagePacket packet);
    void changePlayerId(IdPacket packet);
    void addPlayer(PlayerAddPacket packet);
    void removePlayer(PlayerRemovePacket packet);
    void movePlayer(MovePacket packet);
}
