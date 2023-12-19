package server.entities.enemy.enemyStates;

import common.MoveDirection;
import server.entities.ServerEntity;

public class MoveLeftState implements State {
    ServerEntity enemy;
    int timesMoved;

    public MoveLeftState(ServerEntity enemy, int timesMoved){
        this.enemy = enemy;
        this.timesMoved = timesMoved;
    }

    public void Handle(){
        enemy.move(MoveDirection.LEFT);
        if(timesMoved == 11){
            enemy.setState(new WaitState(enemy, MoveDirection.DOWN, timesMoved+1, MoveDirection.LEFT));
        } else{
            enemy.setState(new WaitState(enemy, MoveDirection.LEFT, timesMoved+1, MoveDirection.LEFT));
        }
        
    } 
}
