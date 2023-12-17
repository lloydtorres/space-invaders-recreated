package server.entities;

import common.EntityType;
import common.MoveDirection;
import server.visitors.Visitor;

public class ServerEntity implements Entity{
    private static int idCounter = 0;
    private int id;
    private float X, Y;
    private float XSpeed, YSpeed;
    private float width, height;
    private final EntityType entityType;
    protected int pointWorth;

    public ServerEntity(EntityType entityType, float X, float Y) {
        this.entityType = entityType;
        this.id = idCounter++;
        this.X = X;
        this.Y = Y;
    }

    public ServerEntity(ServerEntity serverEntity) {
        this(serverEntity.getEntityType(), serverEntity.getX(), serverEntity.getY());

        height = serverEntity.getHeight();
        width = serverEntity.getWidth();
        XSpeed = serverEntity.getXSpeed();
        YSpeed = serverEntity.getYSpeed();
        pointWorth = serverEntity.pointWorth;
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT:
                X += XSpeed;
                break;
            case LEFT:
                X -= XSpeed;
                break;
            case UP:
                Y -= YSpeed;
                break;
            case DOWN:
                Y += YSpeed;
                break;
        }
    }

    @Override
    public void setSpeed(float XSpeed, float YSpeed) {
        this.XSpeed = XSpeed;
        this.YSpeed = YSpeed;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getId() {
        return id;
    }

    public void setNewId(){
        id = idCounter++;
    }

    public void setX(float x) {
        this.X = x;
    }

    public float getX() {
        return X;
    }

    public void setY(float y) {
        this.Y = y;
    }

    public float getY() {
        return Y;
    }

    public float getXSpeed() {
        return XSpeed;
    }

    public float getYSpeed() {
        return YSpeed;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWidth() {
        return width;
    }

    public int getPointWorth() {
        return pointWorth;
    }

    public void setPointWorth(int points) {
        this.pointWorth = points;
    }

    public boolean intersects(Entity other) {
        return this.getX() < other.getX() + other.getWidth() &&
                this.getX() + this.getWidth() > other.getX() &&
                this.getY() < other.getY() + other.getHeight() &&
                this.getY() + this.getHeight() > other.getY();
    }
}
