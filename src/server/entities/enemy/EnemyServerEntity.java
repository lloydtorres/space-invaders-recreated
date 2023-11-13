package server.entities.enemy;

import common.EntityType;
import server.entities.ServerEntity;

public class EnemyServerEntity extends ServerEntity implements Cloneable, IEnemy {
    private final int pointWorth;

    public EnemyServerEntity(float X, float Y) {
        super(EntityType.ENEMY, X, Y, 37, 24, 18, 24);
        this.pointWorth = 5;
    }

    public EnemyServerEntity(EnemyServerEntity e) {
        super(EntityType.ENEMY, e.getX(), e.getY(), e.getWidth(), e.getHeight(), e.getXSpeed(), e.getYSpeed());
        this.pointWorth = e.pointWorth;
    }

    public int getPointWorth() {
        return pointWorth;
    }

    @Override
    public EnemyServerEntity getOriginalEntity() {
        return this;
    }

    public EnemyServerEntity shallowCopy() {
        try {
            return (EnemyServerEntity) this.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
            return this;
        }
    }

    public EnemyServerEntity deepCopy() {
        return new EnemyServerEntity(this);
    }
}
