package server.entities.enemy;

public abstract class EnemyDecorator implements IEnemy {
    protected IEnemy enemy;

    public EnemyDecorator(IEnemy enemy) {
        this.enemy = enemy;
    }

    public abstract int getPointWorth();

    @Override
    public final EnemyServerEntity getOriginalEntity() {
        return enemy.getOriginalEntity();
    }

}
