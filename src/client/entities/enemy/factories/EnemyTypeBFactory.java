package client.entities.enemy.factories;

import client.entities.enemy.types.Enemy;
import client.entities.enemy.types.EnemyTypeB;

public class EnemyTypeBFactory extends EnemyFactory {
    @Override
    public Enemy createEnemy() {
        return new EnemyTypeB();
    }
}