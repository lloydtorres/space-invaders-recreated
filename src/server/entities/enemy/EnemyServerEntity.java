package server.entities.enemy;

import common.EntityType;
import server.entities.ServerEntity;
import server.visitors.Visitor;

public class EnemyServerEntity extends ServerEntity {

    public EnemyServerEntity(float X, float Y) {
        super(EntityType.ENEMY, X, Y);
        this.pointWorth = 5;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitEnemy(this);
    }
}
