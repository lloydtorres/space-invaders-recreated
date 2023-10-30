package client;

import common.Configuration;
import common.GameContext;
import common.IdPacket;
import common.MessagePacket;
import server.ClientHandler;

public class ClientGameContext implements GameContext {
    private SpaceInvadersFrame spaceInvadersFrame;
    private ClientPlayer thisPlayer;
    public ClientGameContext(SpaceInvadersFrame spaceInvadersFrame, ClientPlayer clientPlayer){
        this.spaceInvadersFrame = spaceInvadersFrame;
        thisPlayer = clientPlayer;
    }
    @Override
    public void processReceivedMessage(MessagePacket packet) {
        spaceInvadersFrame.appendToLog(packet.getMessage());
    }

    @Override
    public void changePlayerId(IdPacket packet) {
        if(packet.getSenderId() == Configuration.SERVER_ID){
            thisPlayer.setId(packet.getNewId());
            spaceInvadersFrame.appendToLog("This client's ID on server is " + packet.getNewId());
        }
    }
}
