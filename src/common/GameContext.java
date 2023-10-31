package common;

import common.packets.*;

// Game context interface that is used on client and server. These methods allow packets to make changes on client or server
public interface GameContext {
    void processMessagePacket(MessagePacket packet);
    void processIdPacket(IdPacket packet);
    void processPlayerAddPacket(PlayerAddPacket packet);
    void processPlayerRemovePacket(PlayerRemovePacket packet);
    void processMovePacket(MovePacket packet);
}
