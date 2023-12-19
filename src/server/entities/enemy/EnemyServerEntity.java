package server.entities.enemy;

import common.EntityType;
import server.entities.ServerEntity;
import server.visitors.Visitor;
import server.entities.enemy.enemyStates.State;
import server.entities.enemy.enemyStates.WaitState;

public class EnemyServerEntity extends ServerEntity {
    State state;

    public EnemyServerEntity(float X, float Y) {
        super(EntityType.ENEMY, X, Y);
        this.pointWorth = 5;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitEnemy(this);
    }
}
