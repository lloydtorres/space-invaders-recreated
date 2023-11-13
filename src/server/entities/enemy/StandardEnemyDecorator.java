package server.entities.enemy;

public class StandardEnemyDecorator extends EnemyDecorator {
    public StandardEnemyDecorator(IEnemy enemy) {
        super(enemy);
    }

    @Override
    public int getPointWorth() {
        return enemy.getPointWorth() * 2;
    }
}
