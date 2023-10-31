package common;

import common.packets.Packet;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// Packet handler receives packets into a queue (usually from Server or Client handlers)
// and calls each one's handle method passing gameContext.
public class PacketHandler implements Runnable{
    private GameContext gameContext;
    private BlockingQueue<Packet> packetQueue;
    public PacketHandler(GameContext gameContext) {
        packetQueue = new LinkedBlockingQueue<>();
        this.gameContext = gameContext;
    }
    public void dispatchPacket(Packet packet) {
        try {
            packetQueue.put(packet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Packet packet = packetQueue.take(); // Wait for and retrieve incoming packet
                // Dispatch the packet for processing using the game context
                packet.handle(gameContext);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
