package server.entities.enemy;

import server.entities.ServerEntity;
import server.visitors.Visitor;

public class BasicEnemyDecorator extends EnemyDecorator {
    public BasicEnemyDecorator(ServerEntity serverEntity) {
        super(serverEntity);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitBasicEnemy(this);
    }
}
