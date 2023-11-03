package server.entities;

import server.GameState;

public abstract class Entity {
    private int id;
    private float X, Y;
    private float width, height;
    public Entity(int id, float X, float Y, float width, float height){
        this.id = id;
        this.X = X;
        this.Y = Y;
        this.width = width;
        this.height = height;
    }
    public void update(GameState gameState){
        return;
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
    public boolean intersects(Entity other) {
        return this.getX() < other.getX() + other.getWidth() &&
                this.getX() + this.getWidth() > other.getX() &&
                this.getY() < other.getY() + other.getHeight() &&
                this.getY() + this.getHeight() > other.getY();
    }

}
