package server;

import common.GameContext;
import common.IdPacket;
import common.MessagePacket;

public class ServerGameContext implements GameContext {
    @Override
    public void processReceivedMessage(MessagePacket packet) {
        return;
    }

    @Override
    public void changePlayerId(IdPacket packet) {
        return;
    }
}
