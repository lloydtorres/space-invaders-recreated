package client.particles;

import java.awt.*;

class Particle {
    private float x, y, xSpeed, ySpeed;
    private int size;
    private Color color;

    public Particle(float x, float y, float xSpeed, float ySpeed, int size, Color color) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;
        this.color = color;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public void setSize(int size){
        this.size = size;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public float getX(){
        return x;
    }
    public float getY() {
        return y;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public Color getColor(){
        return color;
    }
    public void move(double deltaTime){
        x += xSpeed * deltaTime;
        y += ySpeed * deltaTime;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)x, (int)y, size, size);
    }
}