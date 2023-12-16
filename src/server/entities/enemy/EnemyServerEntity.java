package server.entities.enemy;

import common.EntityType;
import server.entities.ServerEntity;

public class EnemyServerEntity extends ServerEntity {

    public EnemyServerEntity(float X, float Y) {
        super(EntityType.ENEMY, X, Y, 37, 24, 18, 24);
        this.pointWorth = 5;
    }
}
