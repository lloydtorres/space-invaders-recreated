package server.entities.enemy;

import server.entities.ServerEntity;

public class StandardEnemyDecorator extends EnemyDecorator {
    public StandardEnemyDecorator(ServerEntity serverEntity) {
        super(serverEntity);
    }

    @Override
    public int getPointWorth() {
        return serverEntity.getPointWorth() * 4;
    }
}
