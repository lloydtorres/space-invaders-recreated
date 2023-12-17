package server.visitors;

import server.entities.BulletServerEntity;
import server.entities.Entity;
import server.entities.PlayerServerEntity;
import server.entities.ShieldFragmentServerEntity;
import server.entities.enemy.BasicEnemyDecorator;
import server.entities.enemy.EliteEnemyDecorator;
import server.entities.enemy.EnemyServerEntity;
import server.entities.enemy.StandardEnemyDecorator;

public class DimensionSetterVisitor implements Visitor{

    @Override
    public void visitEnemy(EnemyServerEntity enemy) {
        enemy.setHeight(37);
        enemy.setWidth(24);
    }

    @Override
    public void visitBasicEnemy(BasicEnemyDecorator enemy) {
        enemy.setHeight(37);
        enemy.setWidth(24);
    }

    @Override
    public void visitEliteEnemy(EliteEnemyDecorator enemy) {
        enemy.setHeight(37);
        enemy.setWidth(24);
    }

    @Override
    public void visitStandardEnemy(StandardEnemyDecorator enemy) {
        enemy.setHeight(37);
        enemy.setWidth(24);
    }

    @Override
    public void visitBullet(BulletServerEntity bullet) {
        bullet.setHeight(2);
        bullet.setWidth(14);
    }

    @Override
    public void visitPlayer(PlayerServerEntity player) {
        player.setHeight(38);
        player.setWidth(25);
    }

    @Override
    public void visitShieldFragment(ShieldFragmentServerEntity shieldFragment) {
        shieldFragment.setHeight(10);
        shieldFragment.setWidth(10);
    }

    @Override
    public void visit(Entity entity) {
        entity.setHeight(0);
        entity.setWidth(0);
    }
}
