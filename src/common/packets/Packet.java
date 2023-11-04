package common.packets;

import common.GameContext;

import java.io.Serializable;

public abstract class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    private PacketType packetType;
    private int senderId;

    public Packet(PacketType packetType, int senderId) {
        this.packetType = packetType;
        this.senderId = senderId;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public int getSenderId() {
        return senderId;
    }

    public void handle(GameContext context){
        context.processPacket(this);
    }
}
