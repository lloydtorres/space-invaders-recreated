package common;

public interface GameContext {
    void processReceivedMessage(MessagePacket packet);
    void changePlayerId(IdPacket packet);
}
