package common;

public class MessagePacket extends Packet{
    private String message;
    public MessagePacket(int packetType, int senderId, String message){
        super(packetType, senderId);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    @Override
    public void handle(GameContext context) {
        context.processReceivedMessage(this);
    }
}
