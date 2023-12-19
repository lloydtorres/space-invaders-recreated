package server.entities.enemy.enemyGenerator;

import server.entities.Entity;
import server.entities.enemy.BasicEnemyDecorator;
import server.entities.enemy.EliteEnemyDecorator;
import server.entities.enemy.EnemyServerEntity;
import server.entities.enemy.StandardEnemyDecorator;
import server.entities.enemy.enemyStates.WaitState;

public class RealEnemyEntityGenerator implements EnemyEntityGenerator {
    @Override
    public Entity generateEnemy(int i, int x, int y) {
        if (i == 0) {
            return new EliteEnemyDecorator(new EnemyServerEntity(x, y));
        } else if (i < 3) {
            return new StandardEnemyDecorator(new EnemyServerEntity(x, y));
        } else {
            return new BasicEnemyDecorator(new EnemyServerEntity(x, y));
        }
    }
}
