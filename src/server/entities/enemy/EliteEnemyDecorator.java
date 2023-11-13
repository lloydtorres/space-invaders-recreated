package server.entities.enemy;

public class EliteEnemyDecorator extends EnemyDecorator {
    public EliteEnemyDecorator(IEnemy enemy) {
        super(enemy);
    }

    @Override
    public int getPointWorth() {
        return enemy.getPointWorth() * 3;
    }
}
