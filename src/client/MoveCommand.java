package client;

import common.MoveDirection;
import common.packets.builders.MovePacketBuilder;

public class MoveCommand implements Command{
    private Client client;
    private MoveDirection moveDirection;

    public MoveCommand(Client client, MoveDirection moveDirection){
        this.client = client;
        this.moveDirection = moveDirection;

    }
    @Override
    public void execute() {
        client.sendPacket(new MovePacketBuilder().setMoveDirection(moveDirection).setSenderId(client.getThisPlayer().getId()).getResult());
    }

    @Override
    public void undo() {
        MoveDirection undoDirection = null;
        switch (moveDirection){
            case RIGHT:
                undoDirection = MoveDirection.LEFT;
                break;
            case LEFT:
                undoDirection = MoveDirection.RIGHT;
                break;
            case DOWN:
                undoDirection = MoveDirection.UP;
                break;
            case UP:
                undoDirection = MoveDirection.DOWN;
                break;
        }
        if(moveDirection != null){
            client.sendPacket(new MovePacketBuilder().setMoveDirection(undoDirection).setSenderId(client.getThisPlayer().getId()).getResult());
        }
    }

}
