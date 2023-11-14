package server.entities;

import common.EntityType;
import common.MoveDirection;

public abstract class ServerEntity {
    private static int idCounter = 0;
    private final int id;
    private float X, Y;
    private final float XSpeed, YSpeed;
    private float width, height;
    private EntityType entityType;

    public ServerEntity(EntityType entityType, float X, float Y, float width, float height, float XSpeed, float YSpeed) {
        this.entityType = entityType;
        this.id = idCounter++;
        this.X = X;
        this.Y = Y;
        this.width = width;
        this.height = height;
        this.XSpeed = XSpeed;
        this.YSpeed = YSpeed;
    }

    public ServerEntity(ServerEntity serverEntity) {
        this(serverEntity.getEntityType(), serverEntity.getX(), serverEntity.getY(), serverEntity.getWidth(),
                serverEntity.getHeight(), serverEntity.getXSpeed(), serverEntity.getYSpeed());
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

    public EntityType getEntityType() {
        return entityType;
    }

    public int getId() {
        return id;
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
        return 0;
    }

    public boolean intersects(ServerEntity other) {
        return this.getX() < other.getX() + other.getWidth() &&
                this.getX() + this.getWidth() > other.getX() &&
                this.getY() < other.getY() + other.getHeight() &&
                this.getY() + this.getHeight() > other.getY();
    }
}
