package client;

import common.*;
import common.packets.ToClient.IdPacket;
import common.packets.ToClient.MessagePacket;
import common.packets.ToClient.PlayerAddPacket;
import common.packets.ToClient.PlayerRemovePacket;
import common.packets.ToServer.MovePacket;
import common.packets.ToServer.ShotPacket;

public class ClientGameContext implements GameContext {
    private GameFrame gameFrame;
    private ClientPlayer thisPlayer;
    private Client client;

    public ClientGameContext(GameFrame gameFrame, ClientPlayer clientPlayer, Client client) {
        this.gameFrame = gameFrame;
        thisPlayer = clientPlayer;
        this.client = client;
    }

    @Override
    public void processMessagePacket(MessagePacket packet) {
        gameFrame.appendToLog(packet.getMessage());
    }

    @Override
    public void processIdPacket(IdPacket packet) {
        if (packet.getSenderId() == Configuration.SERVER_ID) {
            thisPlayer.setId(packet.getNewId());
            gameFrame.appendToLog("This client's ID on server is " + packet.getNewId());
        }
    }

    @Override
    public void processPlayerAddPacket(PlayerAddPacket packet) {
        if(packet.getPlayerId() == thisPlayer.getId()){
            return;
        }
        ClientPlayer clientPlayer = new ClientPlayer(packet.getPlayerName());
        clientPlayer.setId(packet.getPlayerId());
        client.addPlayer(packet.getPlayerId(), clientPlayer);
        gameFrame.getGame().addPlayerCannon(packet.getPlayerId(), packet.getPlayerName());
    }

    @Override
    public void processPlayerRemovePacket(PlayerRemovePacket packet) {
        if (packet.getPlayerId() == thisPlayer.getId()) {
            return;
        }
        client.removePlayer(packet.getPlayerId());
        gameFrame.getGame().removePlayerCannon(packet.getPlayerId());
    }

    @Override
    public void processMovePacket(MovePacket packet){
        if(packet.getSenderId() == thisPlayer.getId()){
            gameFrame.getGame().setThisPlayerPosition(packet.getX());
            return;
        }
        gameFrame.getGame().setPlayerPosition(packet.getSenderId(), packet.getX());
    }

    @Override
    public void processShotPacket(ShotPacket packet) {

    }
}
