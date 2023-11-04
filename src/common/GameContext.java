package common;

import common.packets.ToClient.IdPacket;
import common.packets.ToClient.MessagePacket;
import common.packets.ToClient.PlayerAddPacket;
import common.packets.ToClient.PlayerRemovePacket;
import common.packets.ToServer.MovePacket;
import common.packets.ToServer.ShotPacket;

// Game context interface that is used on client and server. These methods allow packets to make changes on client or server
public interface GameContext {
    void processMessagePacket(MessagePacket packet);
    void processIdPacket(IdPacket packet);
    void processPlayerAddPacket(PlayerAddPacket packet);
    void processPlayerRemovePacket(PlayerRemovePacket packet);
    void processMovePacket(MovePacket packet);
    void processShotPacket(ShotPacket packet);

}
