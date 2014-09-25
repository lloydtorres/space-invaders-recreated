import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

///// CANNON CLASS (CONTROLS AND DRAWS USER'S CANNON)
public class Cannon{
	private Image imgShip = new ImageIcon("sprites/cannon.png").getImage(); // picture of ship
	private int pos = 366; // position on screen
	private int lives = 3; // amount of lives before game over
	private Rectangle rectCannon = new Rectangle(pos-12,570,38,25); // rectangle used for collision

	public Cannon(){
    }

	public int getPos(){
		return pos;
	} // returns user position

    // these two functions move user left or right by 5 units
	public void right(){
		pos += 5;
		updateRect();
	}

	public void left(){
		pos -= 5;
		updateRect();
	}

	private void updateRect(){
		rectCannon = new Rectangle(pos-12,570,38,25);
	} // updates collision rectangle

	public int getLives(){ // returns # of user lives
		return lives;
	}

	public void addLife(){
		lives += 1;
	} // adds 1 life to user

	public boolean collide(ArrayList<Bullet> getBullets){ // used to see if user collides with any bullets
		boolean gotShot = false; // flag to determine if user has been hit
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
			pos = 366;
		}
		return gotShot;
	}


	public void draw(Graphics g){
		g.drawImage(imgShip,pos-12,570,null);
	}

}
