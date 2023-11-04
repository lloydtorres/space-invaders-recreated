package client;

import common.Configuration;
import common.GameContext;
import common.packets.Packet;
import common.packets.ToClient.*;

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
    public void processPacket(Packet packet) {
        switch (packet.getPacketType()) {
            case ID:
                processIdPacket((IdPacket) packet);
                break;
            case MESSAGE:
                processMessagePacket((MessagePacket) packet);
                break;
            case PLAYER_ADD:
                processPlayerAddPacket((PlayerAddPacket) packet);
                break;
            case PLAYER_REMOVE:
                processPlayerRemovePacket((PlayerRemovePacket) packet);
                break;
            case SCORE_UPDATE:
                processScoreUpdatePacket((ScoreUpdatePacket) packet);
                break;
            case LIVES_LEFT_UPDATE:
                processLivesLeftUpdatePacket((LivesLeftUpdatePacket) packet);
                break;
            case ENTITY_UPDATE:
                processEntityUpdatePacket((EntityUpdatePacket) packet);
                break;
            case ENTITY_REMOVE:
                processEntityRemovePacket((EntityRemovePacket) packet);
                break;
        }
    }

    private void processMessagePacket(MessagePacket packet) {
        gameFrame.appendToLog(packet.getMessage());
    }

    private void processIdPacket(IdPacket packet) {
        if (packet.getSenderId() == Configuration.SERVER_ID) {
            thisPlayer.setId(packet.getNewId());
            gameFrame.appendToLog("This client's ID on server is " + packet.getNewId());
        }
    }

    private void processPlayerAddPacket(PlayerAddPacket packet) {
        if (packet.getPlayerId() == thisPlayer.getId()) {
            return;
        }
        ClientPlayer clientPlayer = new ClientPlayer(packet.getPlayerName());
        clientPlayer.setId(packet.getPlayerId());
        client.addPlayer(packet.getPlayerId(), clientPlayer);
        gameFrame.getGame().addPlayerCannon(packet.getPlayerId(), packet.getPlayerName());
    }

    private void processPlayerRemovePacket(PlayerRemovePacket packet) {
        if (packet.getPlayerId() == thisPlayer.getId()) {
            return;
        }
        client.removePlayer(packet.getPlayerId());
        gameFrame.getGame().removePlayerCannon(packet.getPlayerId());
    }

    private void processScoreUpdatePacket(ScoreUpdatePacket packet) {
        // code for shown score
        return;
    }

    private void processLivesLeftUpdatePacket(LivesLeftUpdatePacket packet) {
        // code for updating shown lives left
        return;
    }

    private void processEntityUpdatePacket(EntityUpdatePacket packet) {
        if (packet.getEntityId() == thisPlayer.getId()) {
            gameFrame.getGame().setThisPlayerPosition((int) packet.getNewX());
        }

        // entity update code should be handled here
        // entity update packet includes entity id and its type to easily find the entity on the client
        // if entity was not found, then it should be added to the game
        return;
    }

    private void processEntityRemovePacket(EntityRemovePacket packet) {
        // entity removal code
        return;
    }

}
