package server;

import common.MoveDirection;

public class GameLoop implements Runnable{
    private long lastFrameTime;
    private GameState state;
    private double timeStep;
    private boolean gameRunning;
    public GameLoop(int refreshRate){
        lastFrameTime = System.currentTimeMillis();
        timeStep = 1000.0 / refreshRate;
        state = new GameState();
    }

    private void loop(){
        state.initializeGame();
        while(gameRunning){
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - lastFrameTime;
            if (deltaTime < timeStep) {
                continue;
            }
            state.updateBullets();
            //this should be called according to a timer or smth
            //state.updateEnemies(MoveDirection.LEFT);
            state.checkBulletCollisions();
            lastFrameTime = currentTime;
        }
    }

    public GameState getState() {
        return state;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    @Override
    public void run() {
        loop();
    }
}
