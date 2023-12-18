package server.entities.enemy.enemyGenerator;

import server.entities.Entity;

public interface EnemyEntityGenerator {
    Entity generateEnemy(int i, int x, int y);
}
