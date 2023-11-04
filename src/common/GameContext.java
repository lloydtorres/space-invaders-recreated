package common;

import common.packets.Packet;
import common.packets.ToClient.IdPacket;
import common.packets.ToClient.MessagePacket;
import common.packets.ToClient.PlayerAddPacket;
import common.packets.ToClient.PlayerRemovePacket;
import common.packets.ToServer.MovePacket;
import common.packets.ToServer.ShootPacket;

// Game context interface that is used on client and server. These methods allow packets to make changes on client or server
public interface GameContext {
    void processPacket(Packet packet);

}
