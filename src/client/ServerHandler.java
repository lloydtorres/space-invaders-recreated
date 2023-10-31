package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import common.*;
import common.packets.Packet;

// Almost the same as Client handler
public class ServerHandler implements Runnable {
    private final Socket socket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private final BlockingQueue<Packet> outgoingPacketQueue = new LinkedBlockingQueue<>();
    private PacketHandler packetHandler;
    public ServerHandler(Socket socket, PacketHandler packetHandler) {
        this.socket = socket;
        this.packetHandler = packetHandler;
        try{
            outStream = new ObjectOutputStream(this.socket.getOutputStream());
            inStream = new ObjectInputStream(this.socket.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void enqueuePacket(Packet packet) {
        outgoingPacketQueue.offer(packet);
    }
    private void sendPacket(Packet packet) {
        try {
            outStream.writeObject(packet);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendPlayerName(String playerName){
        try{
            outStream.writeObject(playerName);
            outStream.flush();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    private Packet receivePacket() throws IOException, ClassNotFoundException {
        Packet packet;
        packet = (Packet) inStream.readObject();
        return packet;
    }

    @Override
    public void run() {
        Thread senderThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Packet outgoingPacket = outgoingPacketQueue.poll();
                if (outgoingPacket != null) {
                    sendPacket(outgoingPacket);
                }
            }
        });

        Thread receiverThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Packet incomingPacket = receivePacket();
                    packetHandler.dispatchPacket(incomingPacket);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        senderThread.start();
        receiverThread.start();
        try {
            senderThread.join();
            receiverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
