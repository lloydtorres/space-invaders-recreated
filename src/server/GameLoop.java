package server;

public class GameLoop implements Runnable{
    private long lastFrameTime;
    private GameState state;
    private double timeStep;
    public GameLoop(int refreshRate){
        lastFrameTime = System.currentTimeMillis();
        timeStep = 1000.0 / refreshRate;
        state = new GameState();
    }

    private void loop(){
        while(true){
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - lastFrameTime;

            if (deltaTime >= timeStep) {
                state.update();
                lastFrameTime = currentTime;
            }

        }
    }
    @Override
    public void run() {
        loop();
    }
}
