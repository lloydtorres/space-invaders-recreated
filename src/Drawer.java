import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

///// JPANEL CLASS (DRAWS GRAPHICS, LISTENS FOR KEY INPUT)
public class Drawer extends JPanel implements KeyListener {

	private Cannon ship; // game elements generated in main class
	private AlienTrackr enemies;
	private Bullet shot; // bullet shot by user
	private Scorekeeper scoreMan;
	private Shield shield;

	private boolean[]keys; // holds keyboard input
	private boolean canShoot = true; // flag to see if user can shoot bullet
	private Image back = new ImageIcon("sprites/background.png").getImage();
	private Image end = new ImageIcon("sprites/end.png").getImage();

	public Drawer(Cannon player, AlienTrackr badGuys,Scorekeeper getScore,Shield getShield){
		super();
		keys = new boolean[KeyEvent.KEY_LAST+1];
		ship = player;
		enemies = badGuys;
		scoreMan = getScore;
		shield = getShield;

		setSize(770,652);
		addKeyListener(this);
	}

	public void addNotify(){
		super.addNotify();
		requestFocus();
	}

	public void move(){ // takes in keyboard input and moves user cannon
		if (keys[KeyEvent.VK_RIGHT] && (ship.getPos()+5) <= 740){
			ship.right();
		}
		if (keys[KeyEvent.VK_LEFT] && (ship.getPos()-5) >= 12){
			ship.left();
		}
		if (keys[KeyEvent.VK_SPACE] && canShoot){
            // canShoot flag prevents user from shooting infinite bullets one after another
			shot = new Bullet(ship.getPos(),556,Bullet.UP);
			canShoot = false;
		}
	}

	public void trackBullet(){ // tracks bullets shot by user
		if (shot != null){
			boolean shieldHit = false;
			shieldHit = shield.collide(shot.getRect()); // check if bullet hits shield
			if (shieldHit){
				shot = null;
				canShoot = true;
			}
			else if (shot.getY() <= 40 || enemies.collide(shot.getRect())){ // check if bullet hits enemy or goes out of bounds
				shot = null;
				canShoot = true;
			}
		}
	}

	public void keyTyped(KeyEvent e){} // keyboard listeners
	public void keyPressed(KeyEvent e){
		keys[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e){
		keys[e.getKeyCode()] = false;
	}

	public void paintComponent(Graphics g){ // paints all elements of screen
		if (!enemies.lostYet()){ // while game is still ongoing
			g.drawImage(back,0,0,this);
			shield.draw(g);
			ship.draw(g);
	        if (shot != null){
	        	shot.move(); // move and draw user bullet
	         	shot.draw(g);
	        }
	        enemies.ufoTrack(); // move and draw enemies
	       	enemies.draw(g);

		}
		else{ // when game is over
			g.drawImage(end,0,0,this);
		}
		scoreMan.draw(g);
	}

}
