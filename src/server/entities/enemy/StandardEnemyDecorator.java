package server.entities.enemy;

import server.entities.ServerEntity;
import server.visitors.Visitor;

public class StandardEnemyDecorator extends EnemyDecorator {
    public StandardEnemyDecorator(ServerEntity serverEntity) {
        super(serverEntity);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitStandartEnemy(this);
    }
}
