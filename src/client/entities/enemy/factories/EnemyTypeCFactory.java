package client.entities.enemy.factories;

import client.entities.enemy.types.Enemy;
import client.entities.enemy.types.EnemyTypeC;

public class EnemyTypeCFactory extends EnemyFactory{
    @Override
    public Enemy createEnemy() {
        return new EnemyTypeC();
    }
}