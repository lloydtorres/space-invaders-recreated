package common.packets;

import server.GameStateEvent;

public interface IPacketFactory {
    Packet getPacket(int senderId, GameStateEvent event);

    Packet getPacket(int senderId, boolean[] keys);
}
