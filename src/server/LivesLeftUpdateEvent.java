package server;

public class LivesLeftUpdateEvent extends GameStateEvent{
    private int newLivesLeft;
    public LivesLeftUpdateEvent(int newLivesLeft) {
        super(EventType.LIVES_LEFT_UPDATE);
        this.newLivesLeft = newLivesLeft;
    }

    public int getNewLivesLeft() {
        return newLivesLeft;
    }
}
