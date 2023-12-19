package server.entities.enemy.enemyStates;

import common.MoveDirection;
import server.entities.ServerEntity;

public class MoveDownState implements State {
    ServerEntity enemy;
    MoveDirection lastDirection;

    public MoveDownState(ServerEntity enemy, MoveDirection lastDirection){
        this.enemy = enemy;
        this.lastDirection = lastDirection;
    }

    public void Handle(){
        enemy.move(MoveDirection.DOWN);
        
        switch (lastDirection) {
            case RIGHT:
                enemy.setState(new WaitState(enemy, MoveDirection.LEFT, 0, MoveDirection.DOWN));
                break;
            case LEFT:
                enemy.setState(new WaitState(enemy, MoveDirection.RIGHT, 0, MoveDirection.DOWN));
                break;
        }
        
    }
}
