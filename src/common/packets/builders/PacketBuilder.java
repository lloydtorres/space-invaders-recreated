package common.packets.builders;

public interface PacketBuilder {
    PacketBuilder setSenderId(int senderId);

    PacketBuilder reset();
}
