package client.entities.enemy.factories;

import client.entities.enemy.types.Enemy;
import client.entities.enemy.types.EnemyTypeA;

public class EnemyTypeAFactory extends EnemyFactory {
    @Override
    public Enemy createEnemy() {
        return new EnemyTypeA();
    }
}
