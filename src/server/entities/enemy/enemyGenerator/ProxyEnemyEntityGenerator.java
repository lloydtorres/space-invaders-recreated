package server.entities.enemy.enemyGenerator;

import server.entities.Entity;

public class ProxyEnemyEntityGenerator implements EnemyEntityGenerator {
    private final EnemyEntityGenerator realGenerator;

    public ProxyEnemyEntityGenerator(EnemyEntityGenerator realGenerator) {
        this.realGenerator = realGenerator;
    }

    @Override
    public Entity generateEnemy(int i, int x, int y) {
        System.out.println("Generating enemy at (" + x + ", " + y + ")...");

        Entity enemyEntity = realGenerator.generateEnemy(i, x, y);

        System.out.println("Enemy generated: " + enemyEntity.getId());

        return enemyEntity;
    }
}
