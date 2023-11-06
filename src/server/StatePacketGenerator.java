package server;
import common.Configuration;
import common.packets.IPacketFactory;
import common.packets.Packet;

public class StatePacketGenerator implements StateObserver{
    private final Server server;
    private final IPacketFactory packetFactory;
    public StatePacketGenerator(Server server, IPacketFactory packetFactory){
        this.server = server;
        this.packetFactory = packetFactory;
    }

    public void onEvent(GameStateEvent event) {
        Packet packetToSend = packetFactory.getPacket(Configuration.SERVER_ID, event);
        if(packetToSend != null){
            server.broadcastPacket(packetToSend);
        }
    }
}
