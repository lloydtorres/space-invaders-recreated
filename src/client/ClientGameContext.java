package client;

import common.GameContext;
import common.MessagePacket;

public class ClientGameContext implements GameContext {
    private SpaceInvadersFrame spaceInvadersFrame;
    public ClientGameContext(SpaceInvadersFrame spaceInvadersFrame){
        this.spaceInvadersFrame = spaceInvadersFrame;
    }
    @Override
    public void processReceivedMessage(MessagePacket packet) {
        spaceInvadersFrame.appendToLog(packet.getMessage());
    }
}
