package common.packets.builders;

import common.packets.ToClient.MessagePacket;

public class MessagePacketBuilder implements PacketBuilder {
    private MessagePacket messagePacket;

    public MessagePacketBuilder() {
        this.reset();
    }

    @Override
    public MessagePacketBuilder setSenderId(int senderId) {
        messagePacket.setSenderId(senderId);
        return this;
    }

    @Override
    public MessagePacketBuilder reset() {
        messagePacket = new MessagePacket();
        return this;
    }

    public MessagePacketBuilder setMessage(String message) {
        messagePacket.setMessage(message);
        return this;
    }

    public MessagePacket getResult() {
        return messagePacket;
    }
}
