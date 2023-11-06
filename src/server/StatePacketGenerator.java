package server;
import common.Configuration;
import common.packets.Packet;
import common.packets.ToClient.EntityRemovePacket;
import common.packets.ToClient.EntityUpdatePacket;
import common.packets.ToClient.LivesLeftUpdatePacket;
import common.packets.ToClient.ScoreUpdatePacket;

public class StatePacketGenerator implements StateObserver{
    private Server server;
    public StatePacketGenerator(Server server){
        this.server = server;
    }

    public void onEvent(GameStateEvent event) {
        Packet packetToSend = null;
        switch (event.getEventType()){
            case SCORE_UPDATE:
                packetToSend = new ScoreUpdatePacket(Configuration.SERVER_ID, ((ScoreUpdateEvent)event).getNewScore());
                break;
            case LIVES_LEFT_UPDATE:
                packetToSend = new LivesLeftUpdatePacket(Configuration.SERVER_ID, ((LivesLeftUpdateEvent)event).getNewLivesLeft());
                break;
            case ENTITY_UPDATE:
                EntityUpdateEvent temp = (EntityUpdateEvent) event;
                if(temp.isRemoval()){
                    packetToSend = new EntityRemovePacket(Configuration.SERVER_ID, temp.getEntity().getEntityType(), temp.getEntity().getId());
                }else{
                    packetToSend = new EntityUpdatePacket(Configuration.SERVER_ID, temp.getEntity().getEntityType(),
                            temp.getEntity().getId(), temp.getEntity().getX(), temp.getEntity().getY());
                }
                break;
        }
        if(packetToSend != null){
            server.broadcastPacket(packetToSend);
        }
    }
}
