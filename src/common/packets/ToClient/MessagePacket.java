package common.packets.ToClient;

import common.GameContext;
import common.PacketTypes;
import common.packets.Packet;

public class MessagePacket extends Packet {
    private String message;
    public MessagePacket(int senderId, String message){
        super(PacketTypes.MESSAGE, senderId);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    @Override
    public void handle(GameContext context) {
        context.processMessagePacket(this);
    }
}
