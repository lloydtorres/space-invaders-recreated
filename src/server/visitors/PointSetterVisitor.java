package server.visitors;

import server.entities.BulletServerEntity;
import server.entities.PlayerServerEntity;
import server.entities.ServerEntity;
import server.entities.ShieldFragmentServerEntity;
import server.entities.enemy.BasicEnemyDecorator;
import server.entities.enemy.EliteEnemyDecorator;
import server.entities.enemy.EnemyServerEntity;
import server.entities.enemy.StandardEnemyDecorator;

public class PointSetterVisitor implements Visitor {
    @Override
    public void visit(EnemyServerEntity enemy) {
        enemy.setPointWorth(5);
    }

    @Override
    public void visit(BasicEnemyDecorator enemy) {
        enemy.setPointWorth(enemy.getPointWorth() * 2);
    }

    @Override
    public void visit(EliteEnemyDecorator enemy) {
        enemy.setPointWorth(enemy.getPointWorth() * 10);
    }

    @Override
    public void visit(StandardEnemyDecorator enemy) {
        enemy.setPointWorth(enemy.getPointWorth() * 4);
    }

    @Override
    public void visit(BulletServerEntity bullet) {
        bullet.setPointWorth(0);
    }

    @Override
    public void visit(PlayerServerEntity player) {
        player.setPointWorth(0);
    }

    @Override
    public void visit(ShieldFragmentServerEntity shieldFragment) {
        shieldFragment.setPointWorth(0);
    }

    @Override
    public void visit(ServerEntity entity) {
        entity.setPointWorth(0);
    }
}
