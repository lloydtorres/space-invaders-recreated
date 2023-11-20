package client;

import common.packets.builders.ShootPacketBuilder;

class ShootCommand implements Command {
    private Client client;

    public ShootCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute() {
        client.sendPacket(new ShootPacketBuilder().setSenderId(client.getThisPlayer().getId()).getResult());
    }

    @Override
    public void undo() {
        System.out.println("Cannot undo shoot!");
    }
}
