package client;

import common.*;

public class ClientGameContext implements GameContext {
    private GameFrame gameFrame;
    private ClientPlayer thisPlayer;
    private Client client;
    public ClientGameContext(GameFrame gameFrame, ClientPlayer clientPlayer, Client client){
        this.gameFrame = gameFrame;
        thisPlayer = clientPlayer;
        this.client = client;
    }
    @Override
    public void processReceivedMessage(MessagePacket packet) {
        gameFrame.appendToLog(packet.getMessage());
    }

    @Override
    public void changePlayerId(IdPacket packet) {
        if(packet.getSenderId() == Configuration.SERVER_ID){
            thisPlayer.setId(packet.getNewId());
            gameFrame.appendToLog("This client's ID on server is " + packet.getNewId());
        }
    }

    @Override
    public void addPlayer(PlayerAddPacket packet) {
        if(packet.getPlayerId() == thisPlayer.getId()){
            return;
        }
        ClientPlayer clientPlayer = new ClientPlayer(packet.getPlayerName());
        clientPlayer.setId(packet.getPlayerId());
        client.addPlayer(packet.getPlayerId(), clientPlayer);
        gameFrame.getGame().addPlayerCannon(packet.getPlayerId(), packet.getPlayerName());
    }

    @Override
    public void removePlayer(PlayerRemovePacket packet) {
        if(packet.getPlayerId() == thisPlayer.getId()){
            return;
        }
        client.removePlayer(packet.getPlayerId());
        gameFrame.getGame().removePlayerCannon(packet.getPlayerId());
    }
    @Override
    public void movePlayer(MovePacket packet){
        if(packet.getSenderId() == thisPlayer.getId()){
            return;
        }
        gameFrame.getGame().setPlayerPosition(packet.getSenderId(), packet.getxCoord());
    }
}
