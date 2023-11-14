package server.entities.enemy;

import server.entities.ServerEntity;

public class BasicEnemyDecorator extends EnemyDecorator {
    public BasicEnemyDecorator(ServerEntity serverEntity) {
        super(serverEntity);
    }

    @Override
    public int getPointWorth() {
        return serverEntity.getPointWorth() * 2;
    }
}
