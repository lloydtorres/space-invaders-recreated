package client;

import common.MovePacket;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

///// CANNON CLASS (CONTROLS AND DRAWS USER'S CANNON)
public class PlayerCannon {
    private int pos = 366; // position on screen
    private int lives = 3; // amount of lives before game over
    private Rectangle rectCannon = new Rectangle(pos-12,570,38,25); // rectangle used for collision
    private String name;
    private Image imgShip = new ImageIcon("sprites/cannon.png").getImage(); // picture of ship
    private Image shipDown0 = new ImageIcon("sprites/c_broken1.png").getImage(); // broken ships
    private Image shipDown1 = new ImageIcon("sprites/c_broken2.png").getImage();
    private int curImage = 0; // determines which broken image will be displayed

    private boolean gotShot = false; // flag to determine if user has been hit
    private int counter = 0; // used to moderate when to display image
    private Client client;

    public PlayerCannon(String name, Client client){
        this.name = name;
        this.client = client;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPos(){
        return pos;
    } // returns user position

    public void setPos(int pos) {
        this.pos = pos;
        updateRect();
    }

    // these two functions move user left or right by 5 units
    public void right(){
        pos += 5;
        client.sendPacket(new MovePacket(client.getThisPlayer().getId(), pos));
        updateRect();
    }

    public void left(){
        pos -= 5;
        client.sendPacket(new MovePacket(client.getThisPlayer().getId(), pos));
        updateRect();
    }

    private void updateRect(){
        rectCannon = new Rectangle(pos-12,570,38,25);
    } // updates collision rectangle

    public int getLives(){ // returns # of user lives
        return lives;
    }

    public void addLife(){ // adds 1 life to user
        if (lives < 99){
            lives += 1;
        }
    }

    public boolean gotHit(){
        return gotShot;
    }

    public void collide(ArrayList<Bullet> getBullets){ // used to see if user collides with any bullets
        for (int i=0;i<getBullets.size();i++){
            if (getBullets.get(i) != null){
                Rectangle getBulletRect = getBullets.get(i).getRect();
                if (getBulletRect.intersects(rectCannon)){
                    getBullets.set(i,null);
                    gotShot = true;
                }
            }
        }
        if (gotShot){ // if shot, lose 1 life and reset position
            lives -= 1;
            if (lives < 0) lives = 0; // prevents # of lives from becoming negative

        }
    }


    public void draw(Graphics g){
        g.drawString(name, pos - 12, 580);
        if (!gotShot){ // normal ship
            g.drawImage(imgShip,pos-12,570,null);
        }
        // draws broken ship when hit
        else if (gotShot){
            counter++;
            if (counter%5 == 0){
                curImage = 1 - curImage; // flips between 1 and 0
            }
            if (curImage == 0){
                g.drawImage(shipDown0,pos-12,570,null);
            }
            else{
                g.drawImage(shipDown1,pos-12,570,null);
            }
        }
        else {
            pos = 366;
            gotShot = false;
            counter = 0;
        }
    }

}
