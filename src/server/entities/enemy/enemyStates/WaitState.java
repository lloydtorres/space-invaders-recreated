package server.entities.enemy.enemyStates;

import common.MoveDirection;
import server.entities.ServerEntity;
import server.entities.enemy.enemyStates.*;

public class WaitState implements State {
    ServerEntity enemy;
    MoveDirection nextState;
    int timesMoved;
    MoveDirection direction;

    public WaitState(ServerEntity enemy, MoveDirection nextState, int timesMoved, MoveDirection direction){
        this.enemy = enemy;
        this.nextState = nextState;
        this.timesMoved = timesMoved;
        this.direction = direction;
    }

    public void Handle(){
        switch(nextState){
            case LEFT:
                enemy.setState(new MoveLeftState(enemy, timesMoved));
                break;
            case RIGHT:
                enemy.setState(new MoveRightState(enemy, timesMoved));
                break;
            case DOWN:
                enemy.setState(new MoveDownState(enemy, direction));
                break;
        }
    }
}
