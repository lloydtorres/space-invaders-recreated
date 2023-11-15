package common.packets;

import common.MoveDirection;
import common.packets.builders.*;
import server.EntityUpdateEvent;
import server.GameStateEvent;
import server.LivesLeftUpdateEvent;
import server.ScoreUpdateEvent;

import java.awt.event.KeyEvent;

public class PacketFactory implements IPacketFactory {
    @Override
    public Packet getPacket(int senderId, GameStateEvent event) {
        switch (event.getEventType()) {
            case SCORE_UPDATE -> {
                return new ScoreUpdatePacketBuilder()
                        .setSenderId(senderId)
                        .setNewScore(((ScoreUpdateEvent) event).getNewScore())
                        .getResult();
            }
            case LIVES_LEFT_UPDATE -> {
                return new LivesLeftUpdatePacketBuilder()
                        .setSenderId(senderId)
                        .setNewLivesLeft(((LivesLeftUpdateEvent) event).getNewLivesLeft())
                        .getResult();
            }
            case ENTITY_UPDATE -> {
                EntityUpdateEvent temp = (EntityUpdateEvent) event;
                if (temp.isRemoval()) {
                    return new EntityRemovePacketBuilder()
                            .setSenderId(senderId)
                            .setEntityId(temp.getEntity().getId())
                            .setEntityType(temp.getEntity().getEntityType())
                            .getResult();
                } else {
                    return new EntityUpdatePacketBuilder()
                            .setSenderId(senderId)
                            .setEntityId(temp.getEntity().getId())
                            .setEntityType(temp.getEntity().getEntityType())
                            .setNewX(temp.getEntity().getX())
                            .setNewY(temp.getEntity().getY())
                            .getResult();
                }
            }
        }
        return null;
    }

    @Override
    public Packet getPacket(int senderId, boolean[] keys) {
        if (keys == null) {
            throw new NullPointerException("Keys array must not be null");
        }
        if (keys.length != KeyEvent.KEY_LAST + 1) {
            throw new IllegalArgumentException("Length of keys array must be the same as Java AWT Event's KeyEvent id range + 1");
        }
        if (keys[KeyEvent.VK_SPACE]) {
            return new ShootPacketBuilder()
                    .setSenderId(senderId)
                    .getResult();
        }
        if (keys[KeyEvent.VK_LEFT] && keys[KeyEvent.VK_RIGHT]) {
            return null;
        }
        if (keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_RIGHT]) {
            return new MovePacketBuilder()
                    .setSenderId(senderId)
                    .setMoveDirection(keys[KeyEvent.VK_LEFT] ? MoveDirection.LEFT : MoveDirection.RIGHT)
                    .getResult();
        }
        return null;
    }
}
