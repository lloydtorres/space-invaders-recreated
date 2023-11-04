package server.entities;

import common.MoveDirection;

public abstract class ServerEntity {
    private int id;
    private float X, Y;
    private float XSpeed, YSpeed;
    private float width, height;
    public ServerEntity(int id, float X, float Y, float width, float height, float XSpeed, float YSpeed){
        this.id = id;
        this.X = X;
        this.Y = Y;
        this.width = width;
        this.height = height;
        this.XSpeed = XSpeed;
        this.YSpeed = YSpeed;
    }
    public void move(MoveDirection direction){
        switch (direction){
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

    public void setId(int id) {
        this.id = id;
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
    public boolean intersects(ServerEntity other) {
        return this.getX() < other.getX() + other.getWidth() &&
                this.getX() + this.getWidth() > other.getX() &&
                this.getY() < other.getY() + other.getHeight() &&
                this.getY() + this.getHeight() > other.getY();
    }

}
