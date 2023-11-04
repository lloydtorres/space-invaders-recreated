package server;

public class ScoreUpdateEvent extends GameStateEvent{
    private int newScore;
    public ScoreUpdateEvent(int newScore) {
        super(EventType.SCORE_UPDATE);
        this.newScore = newScore;
    }

    public int getNewScore() {
        return newScore;
    }
}
