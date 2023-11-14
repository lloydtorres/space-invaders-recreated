package server.entities.enemy;

import common.EntityType;
import server.entities.ServerEntity;

public class EnemyServerEntity extends ServerEntity {
    private final int pointWorth;

    public EnemyServerEntity(float X, float Y) {
        super(EntityType.ENEMY, X, Y, 37, 24, 18, 24);
        this.pointWorth = 5;
    }

    @Override
    public int getPointWorth() {
        return pointWorth;
    }
}
