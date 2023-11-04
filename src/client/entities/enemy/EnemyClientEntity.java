package client.entities.enemy;

import client.entities.common.ClientEntity;
import client.entities.enemy.factories.EnemyFactory;
import client.entities.enemy.types.Enemy;
import client.utilities.Drawer;

import java.awt.*;

public class EnemyClientEntity extends ClientEntity {
    private Enemy enemy;

    public EnemyClientEntity(int id, Drawer drawer, int x, int y, EnemyFactory factory) {
        super(id, drawer, x, y);

        var enemy = factory.createEnemy();

        rectangle = new Rectangle(x, y, enemy.getSize(), 24);
    }

    public void updateRect(){
        rectangle = new Rectangle(x, y, enemy.getSize(),24);
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
        return enemy.getSize();
    }

    public int getX(){
        return x;
    } // return alien position

    public int getY(){
        return y;
    }

    public Image getImage(int beat){ // method determines which image to display depending on beat value
        if (beat==0) return enemy.getFirstPose();
        else return enemy.getSecondPose();
    }
}
