package server;

public interface StateObserver {
    void onEvent(GameStateEvent event);
}
