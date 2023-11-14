package server.entities.enemy;

import server.entities.ServerEntity;

public class EliteEnemyDecorator extends EnemyDecorator {
    public EliteEnemyDecorator(ServerEntity serverEntity) {
        super(serverEntity);
    }

    @Override
    public int getPointWorth() {
        return serverEntity.getPointWorth() * 10;
    }
}
