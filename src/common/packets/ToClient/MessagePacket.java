package common.packets.ToClient;

import common.packets.Packet;
import common.packets.PacketType;

public class MessagePacket extends Packet {
    private String message;

    public MessagePacket() {
        super(PacketType.MESSAGE);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
