package client;

import common.packets.builders.StateSavePacketBuilder;

class StateSaveCommand implements Command {
    private Client client;

    public StateSaveCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute() {
        client.sendPacket(new StateSavePacketBuilder().setSenderId(client.getThisPlayer().getId()).getResult());
    }

    @Override
    public void undo() {
        System.out.println("Cannot undo state save!");
    }
}
