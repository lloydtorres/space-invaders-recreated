package server.visitors;

import server.entities.BulletServerEntity;
import server.entities.Entity;
import server.entities.PlayerServerEntity;
import server.entities.ShieldFragmentServerEntity;
import server.entities.enemy.BasicEnemyDecorator;
import server.entities.enemy.EliteEnemyDecorator;
import server.entities.enemy.EnemyServerEntity;
import server.entities.enemy.StandardEnemyDecorator;

public class SpeedSetterVisitor implements Visitor{
    @Override
    public void visitEnemy(EnemyServerEntity enemy) {
        enemy.setSpeed(22.5f, 29);
    }

    @Override
    public void visitBasicEnemy(BasicEnemyDecorator enemy) {
        enemy.setSpeed(22.5f, 29);
    }

    @Override
    public void visitEliteEnemy(EliteEnemyDecorator enemy) {
        enemy.setSpeed(22.5f, 29);
    }

    @Override
    public void visitStandardEnemy(StandardEnemyDecorator enemy) {
        enemy.setSpeed(22.5f, 29);
    }

    @Override
    public void visitBullet(BulletServerEntity bullet) {
        bullet.setSpeed(0,10);
    }

    @Override
    public void visitPlayer(PlayerServerEntity player) {
        player.setSpeed(5,0);
    }

    @Override
    public void visitShieldFragment(ShieldFragmentServerEntity shieldFragment) {
        shieldFragment.setSpeed(0,0);
    }

    @Override
    public void visit(Entity entity) {
        entity.setSpeed(0,0);
    }
}
