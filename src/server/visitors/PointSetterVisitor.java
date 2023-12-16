package server.visitors;
import server.entities.*;
import server.entities.enemy.BasicEnemyDecorator;
import server.entities.enemy.EliteEnemyDecorator;
import server.entities.enemy.EnemyServerEntity;
import server.entities.enemy.StandardEnemyDecorator;

public class PointSetterVisitor implements Visitor {
    @Override
    public void visitEnemy(EnemyServerEntity enemy) {
        enemy.setPointWorth(5);
    }

    @Override
    public void visitBasicEnemy(BasicEnemyDecorator enemy) {
        enemy.setPointWorth(10);
    }

    @Override
    public void visitEliteEnemy(EliteEnemyDecorator enemy) { enemy.setPointWorth(50); }

    @Override
    public void visitStandardEnemy(StandardEnemyDecorator enemy) {
        enemy.setPointWorth(20);
    }

    @Override
    public void visitBullet(BulletServerEntity bullet) {
        bullet.setPointWorth(0);
    }

    @Override
    public void visitPlayer(PlayerServerEntity player) {
        player.setPointWorth(0);
    }

    @Override
    public void visitShieldFragment(ShieldFragmentServerEntity shieldFragment) {
        shieldFragment.setPointWorth(0);
    }

    @Override
    public void visit(Entity entity) {
        entity.setPointWorth(0);
    }
}
