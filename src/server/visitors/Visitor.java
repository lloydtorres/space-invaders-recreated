package server.visitors;

import server.entities.*;
import server.entities.enemy.BasicEnemyDecorator;
import server.entities.enemy.EliteEnemyDecorator;
import server.entities.enemy.EnemyServerEntity;
import server.entities.enemy.StandardEnemyDecorator;

public interface Visitor {
    void visitEnemy(EnemyServerEntity enemy);
    void visitBasicEnemy(BasicEnemyDecorator enemy);
    void visitEliteEnemy(EliteEnemyDecorator enemy);
    void visitStandardEnemy(StandardEnemyDecorator enemy);
    void visitBullet(BulletServerEntity bullet);
    void visitPlayer(PlayerServerEntity player);
    void visitShieldFragment(ShieldFragmentServerEntity shieldFragment);
    void visit(Entity entity);
}
