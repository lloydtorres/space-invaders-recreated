package common.packets;

import common.GameContext;

import java.io.Serializable;

public abstract class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    private final PacketType packetType;
    private int senderId;

    public Packet(PacketType packetType) {
        this.packetType = packetType;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int id){
        this.senderId = id;
    }

    public void handle(GameContext context){
        context.processPacket(this);
    }
}
