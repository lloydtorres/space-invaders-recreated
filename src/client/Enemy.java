package client;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

///// ENEMY CLASS (KEEPS TRACK OF EACH INDIVIDUAL ALIEN)
public class Enemy{
    private int x,y,xSize; // enemy position on screen, width of enemy sprite
    private long points; // score value of alien when destroyed
    private Rectangle enemyRect; // collision rectangle for alien
    private Image imgOne; // image used for 1st beat
    private Image imgTwo; // image used for 2nd beat
    private Image toReturn; // determines which image to display

    public Enemy(int type,int posX,int posY){
        if(type == 1){ // top level
            imgOne = new ImageIcon("sprites/a1.png").getImage();
            imgTwo = new ImageIcon("sprites/a2.png").getImage();
            xSize = 24;
            points = 30;
        }
        if(type == 2){ // 2nd and 3rd level
            imgOne = new ImageIcon("sprites/b1.png").getImage();
            imgTwo = new ImageIcon("sprites/b2.png").getImage();
            xSize = 33;
            points = 20;
        }
        if(type == 3){ // 4th and 5th level
            imgOne = new ImageIcon("sprites/c1.png").getImage();
            imgTwo = new ImageIcon("sprites/c2.png").getImage();
            xSize = 37;
            points = 10;
        }
        if(type == 4){ // red UFO
            Random coin = new Random(); // used to randomly determine alien point value
            int posPoints[] = {50,100,150};
            imgOne = imgTwo = new ImageIcon("sprites/d.png").getImage();
            xSize = 51;
            points = posPoints[coin.nextInt(3)];
        }
        x = posX;
        y = posY;
        enemyRect = new Rectangle(x,y,xSize,24);
    }

    public void updateRect(){
        enemyRect = new Rectangle(x,y,xSize,24);
    }

    public void left(){ // move alien to left
        x -= 18;
        updateRect();
    }

    public void smoothLeft(){ // used for the special UFO (moves slower so it looks smoother)
        x -= 6;
        updateRect();
    }

    public void right(){ // move alien to right
        x += 18;
        updateRect();
    }

    public void down(){ // moves down alien
        y += 24;
        updateRect();
    }

    public int getSizeX(){
        return xSize;
    }

    public int getX(){
        return x;
    } // return alien position

    public int getY(){
        return y;
    }

    public Rectangle getRect(){
        return enemyRect;
    }

    public long getScore(){
        return points;
    }

    public Image getImage(int beat){ // method determines which image to display depending on beat value
        if (beat==0){
            toReturn = imgOne;
        }
        else if (beat==1){
            toReturn = imgTwo;
        }
        return toReturn;
    }
}
