package server;

import common.GameContext;
import common.packets.Packet;
import common.packets.ToServer.*;

// Many of these are left blank because server should never receive these packets from clients
public class ServerGameContext implements GameContext {
    private Server server;
    private GameState gameState;
    public ServerGameContext(Server server, GameState gameState){
        this.server = server;
        this.gameState = gameState;
    }
    @Override
    public void processPacket(Packet packet) {
        switch (packet.getPacketType()){
            case MOVE:
                processMovePacket((MovePacket) packet);
                break;
            case SHOOT:
                processShotPacket((ShootPacket) packet);
                break;
            case STATE_SAVE:
                processStateSavePacket((StateSavePacket) packet);
                break;
            case STATE_RESTORE:
                processStateRestorePacket((StateRestorePacket) packet);
                break;
        }
    }
    private void processMovePacket(MovePacket packet) {
        gameState.movePlayer(packet.getSenderId(), packet.getMoveDirection());
    }

    private void processShotPacket(ShootPacket packet) {
        gameState.shootFromPlayer(packet.getSenderId());
    }

    private void processStateSavePacket(StateSavePacket packet){
        GameLoop gameLoop = GameLoop.getInstance();
        gameLoop.saveState();
    }

    private void processStateRestorePacket(StateRestorePacket packet){
        GameLoop gameLoop = GameLoop.getInstance();
        gameLoop.restoreState();
    }
}
