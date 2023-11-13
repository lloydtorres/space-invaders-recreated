package server.entities.enemy;

public class BasicEnemyDecorator extends EnemyDecorator {
    public BasicEnemyDecorator(IEnemy enemy) {
        super(enemy);
    }

    @Override
    public int getPointWorth() {
        return enemy.getPointWorth() + 5;
    }
}
