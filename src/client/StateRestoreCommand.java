package client;

import common.packets.builders.StateRestorePacketBuilder;

class StateRestoreCommand implements Command {
    private Client client;

    public StateRestoreCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute() {
        client.sendPacket(new StateRestorePacketBuilder().setSenderId(client.getThisPlayer().getId()).getResult());
    }

    @Override
    public void undo() {
        System.out.println("Cannot undo state restore!");
    }
}
