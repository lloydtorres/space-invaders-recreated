package server.entities.enemy;

import server.entities.ServerEntity;
import server.visitors.Visitor;

public class EliteEnemyDecorator extends EnemyDecorator {
    public EliteEnemyDecorator(ServerEntity serverEntity) {
        super(serverEntity);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitEliteEnemy(this);
    }
}
