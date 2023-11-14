package server.entities.enemy;

import server.entities.ServerEntity;

public abstract class EnemyDecorator extends ServerEntity {
    protected ServerEntity serverEntity;

    public EnemyDecorator(ServerEntity serverEntity) {
        super(serverEntity);
        this.serverEntity = serverEntity;
    }
}
