package client.entities.enemy.factories;

import client.entities.enemy.types.Enemy;
import client.entities.enemy.types.EnemyTypeD;

public class EnemyTypeDFactory extends EnemyFactory{
    @Override
    public Enemy createEnemy() {
        return new EnemyTypeD();
    }
}
