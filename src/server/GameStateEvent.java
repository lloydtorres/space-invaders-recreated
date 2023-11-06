package server;

public abstract class GameStateEvent {
    private EventType eventType;
    public GameStateEvent(EventType eventType){
        this.eventType = eventType;
    }
    public EventType getEventType() {
        return eventType;
    }
}
