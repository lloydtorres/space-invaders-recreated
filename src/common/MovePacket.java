package common;

public class MovePacket extends Packet{
    private int xCoord;
    public MovePacket(int senderId, int xCoord){
        super(PacketTypes.MOVE, senderId);
        this.xCoord = xCoord;
    }
    @Override
    public void handle(GameContext context) {
        context.movePlayer(this);
    }
    public int getxCoord() {
        return xCoord;
    }
}
