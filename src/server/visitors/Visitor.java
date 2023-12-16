package server.visitors;

import server.entities.BulletServerEntity;
import server.entities.PlayerServerEntity;
import server.entities.ServerEntity;
import server.entities.ShieldFragmentServerEntity;
import server.entities.enemy.BasicEnemyDecorator;
import server.entities.enemy.EliteEnemyDecorator;
import server.entities.enemy.EnemyServerEntity;
import server.entities.enemy.StandardEnemyDecorator;

public interface Visitor {
    void visit(EnemyServerEntity enemy);
    void visit(BasicEnemyDecorator enemy);
    void visit(EliteEnemyDecorator enemy);
    void visit(StandardEnemyDecorator enemy);
    void visit(BulletServerEntity bullet);
    void visit(PlayerServerEntity player);
    void visit(ShieldFragmentServerEntity shieldFragment);
    void visit(ServerEntity entity);
}
