package common.packets;

import common.GameContext;

import java.io.Serializable;

public abstract class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    private int packetType;
    private int senderId;

    public Packet(int packetType, int senderId) {
        this.packetType = packetType;
        this.senderId = senderId;
    }

    public int getPacketType() {
        return packetType;
    }

    public int getSenderId() {
        return senderId;
    }

    public abstract void handle(GameContext context);
}
