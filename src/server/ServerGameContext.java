package server;

import common.*;
import common.packets.ToClient.IdPacket;
import common.packets.ToClient.MessagePacket;
import common.packets.ToClient.PlayerAddPacket;
import common.packets.ToClient.PlayerRemovePacket;
import common.packets.ToServer.MovePacket;
import common.packets.ToServer.ShotPacket;

// Many of these are left blank because server should never receive these packets from clients
public class ServerGameContext implements GameContext {
    private Server server;
    private GameState gameState;
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

    @Override
    public void processMovePacket(MovePacket packet) {
        gameState.movePlayer(packet.getSenderId(), packet.getMoveDirection());
    }

    @Override
    public void processShotPacket(ShotPacket packet) {
        gameState.shootFromPlayer(packet.getSenderId());
    }
}
