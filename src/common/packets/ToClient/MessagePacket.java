package common.packets.ToClient;

import common.GameContext;
import common.PacketTypes;
import common.packets.Packet;
import common.packets.PacketType;

public class MessagePacket extends Packet {
    private String message;
    public MessagePacket(int senderId, String message){
        super(PacketType.MESSAGE, senderId);
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}
