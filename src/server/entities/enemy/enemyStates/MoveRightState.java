package server.entities.enemy.enemyStates;

import common.MoveDirection;
import server.entities.ServerEntity;

public class MoveRightState implements State {
    ServerEntity enemy;
    int timesMoved;

    public MoveRightState(ServerEntity enemy, int timesMoved){
        this.enemy = enemy;
        this.timesMoved = timesMoved;
    }

    public void Handle(){
        enemy.move(MoveDirection.RIGHT);
        if(timesMoved == 11){
            enemy.setState(new WaitState(enemy, MoveDirection.DOWN, timesMoved+1, MoveDirection.RIGHT));
        } else {
            enemy.setState(new WaitState(enemy, MoveDirection.RIGHT, timesMoved+1, MoveDirection.RIGHT));
        }
    }
}
