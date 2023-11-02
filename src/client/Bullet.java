package client;

import java.awt.*;

///// BULLET CLASS (CONTROLS AND DRAWS USER'S BULLETS)
public class Bullet{
    public static final int UP = -1; // used to specify bullet direction on screen
    public static final int DOWN = 1;

    private int x,y,dir; // position and bullet direction
    private Rectangle bulletRect; // used for collisions

    public Bullet(int inX,int inY,int getDir){
        x = inX;
        if (getDir == UP){ // if bullet is going up, must be from user so offset it a bit
            x += 6;
        }
        y = inY;
        dir = getDir;
        bulletRect = new Rectangle(x,y,2,14);
    }

    public void move(){
        if (dir == UP){ // specifies bullet movement and speed
            y -= 10;
        }
        else{
            y += 5;
        }
        bulletRect = new Rectangle(x,y,2,14);
    }

    public int getX(){
        return x;
    } // returns bullet position and collision rectangle

    public int getY(){
        return y;
    }

    public Rectangle getRect(){
        return bulletRect;
    }

    public void draw(Graphics g){
        g.setColor(new Color(255,255,255));
        g.fillRect(x,y,2,14);
    }
}
