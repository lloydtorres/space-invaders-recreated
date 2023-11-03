package server;

import common.*;
import common.packets.*;

// Many of these are left blank because server should never receive these packets from clients
public class ServerGameContext implements GameContext {
    private Server server;
    public ServerGameContext(Server server){
        this.server = server;
    }
    @Override
    public void processMessagePacket(MessagePacket packet) {
        return;
    }
    @Override
    public void processIdPacket(IdPacket packet) {
        return;
    }
    @Override
    public void processPlayerAddPacket(PlayerAddPacket packet) {
        return;
    }
    @Override
    public void processPlayerRemovePacket(PlayerRemovePacket packet) {
        return;
    }

    // basically a placeholder implementation. currently acts like peer to peer communication
    // should be changed when game session is implemented on the server
    @Override
    public void processMovePacket(MovePacket packet) {
        server.updateServerPlayerPosition(packet.getSenderId(), packet.getX());
    }

    @Override
    public void processShotPacket(ShotPacket packet) {

    }
}
