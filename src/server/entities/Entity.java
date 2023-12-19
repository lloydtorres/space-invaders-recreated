package server.entities;
import common.EntityType;
import common.MoveDirection;
import server.visitors.Visitor;
import server.entities.enemy.enemyStates.State;

public interface Entity {
    EntityType getEntityType();
    int getId();
    void setNewId();
    void setX(float x);
    float getX();
    void setY(float y);
    float getY();
    float getXSpeed();
    float getYSpeed();
    void setHeight(float height);
    float getHeight();
    void setWidth(float width);
    float getWidth();
    int getPointWorth();
    void setPointWorth(int points);
    boolean intersects(Entity other);
    void move(MoveDirection direction);
    void process();
    void setState(State state);

    void setSpeed(float XSpeed, float YSpeed);

    void accept (Visitor visitor);
}
