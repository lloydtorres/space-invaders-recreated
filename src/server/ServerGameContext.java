package server;

import common.*;

public class ServerGameContext implements GameContext {
    private Server server;
    public ServerGameContext(Server server){
        this.server = server;
    }
    @Override
    public void processReceivedMessage(MessagePacket packet) {
        return;
    }

    @Override
    public void changePlayerId(IdPacket packet) {
        return;
    }

    @Override
    public void addPlayer(PlayerAddPacket packet) {
        return;
    }
    @Override
    public void removePlayer(PlayerRemovePacket packet) {
        return;
    }

    @Override
    public void movePlayer(MovePacket packet) {
        server.updateServerPlayerPosition(packet.getSenderId(), packet.getxCoord());
        server.broadcastPacket(packet);
    }
}
