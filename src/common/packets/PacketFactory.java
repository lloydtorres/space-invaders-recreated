package common.packets;

import common.MoveDirection;
import common.packets.ToClient.EntityRemovePacket;
import common.packets.ToClient.EntityUpdatePacket;
import common.packets.ToClient.LivesLeftUpdatePacket;
import common.packets.ToClient.ScoreUpdatePacket;
import common.packets.ToServer.MovePacket;
import common.packets.ToServer.ShootPacket;
import server.EntityUpdateEvent;
import server.GameStateEvent;
import server.LivesLeftUpdateEvent;
import server.ScoreUpdateEvent;

import java.awt.event.KeyEvent;

public class PacketFactory implements IPacketFactory {
    @Override
    public Packet getPacket(int senderId, GameStateEvent event) {
        switch (event.getEventType()) {
            case SCORE_UPDATE:
                return new ScoreUpdatePacket(senderId, ((ScoreUpdateEvent) event).getNewScore());
            case LIVES_LEFT_UPDATE:
                return new LivesLeftUpdatePacket(senderId, ((LivesLeftUpdateEvent) event).getNewLivesLeft());
            case ENTITY_UPDATE:
                EntityUpdateEvent temp = (EntityUpdateEvent) event;
                if (temp.isRemoval()) {
                    return new EntityRemovePacket(senderId, temp.getEntity().getEntityType(), temp.getEntity().getId());
                } else {
                    return new EntityUpdatePacket(senderId, temp.getEntity().getEntityType(), temp.getEntity().getId(), temp.getEntity().getX(), temp.getEntity().getY());
                }
        }
        return null;
    }

    @Override
    public Packet getPacket(int senderId, boolean[] keys) {
        if(keys == null) {
            throw new NullPointerException("Keys array must not be null");
        }
        if (keys.length != KeyEvent.KEY_LAST + 1) {
            throw new IllegalArgumentException("Length of keys array must be the same as Java AWT Event's KeyEvent id range + 1");
        }
        if (keys[KeyEvent.VK_SPACE]) {
            return new ShootPacket(senderId);
        }
        if (keys[KeyEvent.VK_LEFT] && keys[KeyEvent.VK_RIGHT]) {
            return null;
        }
        if (keys[KeyEvent.VK_LEFT]) {
            return new MovePacket(senderId, MoveDirection.LEFT);
        }
        if (keys[KeyEvent.VK_RIGHT]) {
            return new MovePacket(senderId, MoveDirection.RIGHT);
        }
        return null;
    }


}
