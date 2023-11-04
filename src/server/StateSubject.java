package server;

public interface StateSubject {
    void addObserver(StateObserver observer);
    void removeObserver(StateObserver observer);
    void notifyObservers(GameStateEvent event);
}
