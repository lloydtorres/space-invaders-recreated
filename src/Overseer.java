import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

///// JPANEL CLASS (DRAWS GRAPHICS, LISTENS FOR KEY INPUT, CALLS FOR MOVES)
public class Overseer extends JPanel implements KeyListener {

	private Cannon ship; // game elements generated in main class
	private AlienMan enemies;
	private Bullet shot; // bullet shot by user
	private Scorekeeper scoreMan;
	private Shield shield;
    private BulletMan shotsFired;

	private boolean[]keys; // holds keyboard input

    private boolean playing = true; // flag to see if game is still ongoing
    private boolean paused = false; // flag for when user pauses/unpauses game
    private boolean restartGame = false; // flag for when user wants to restart the game

	private Image back = new ImageIcon("sprites/background.png").getImage();
    private Image pOverlay = new ImageIcon("sprites/paused.png").getImage();
	private Image end = new ImageIcon("sprites/end.png").getImage();

	public Overseer(Cannon player, AlienMan badGuys, Scorekeeper getScore, Shield getShield, BulletMan getShots){
		super();
		keys = new boolean[KeyEvent.KEY_LAST+1];
		ship = player;
		enemies = badGuys;
		scoreMan = getScore;
		shield = getShield;
        shotsFired = getShots;

		setSize(770,652);
		addKeyListener(this);
	}

	public void addNotify(){
		super.addNotify();
		requestFocus();
	}

	public void move(){ // takes in keyboard input and moves user cannon
        if (keys[KeyEvent.VK_RIGHT] && (ship.getPos() + 5) <= 740) {
            ship.right();
        }
        if (keys[KeyEvent.VK_LEFT] && (ship.getPos() - 5) >= 12) {
            ship.left();
        }
        if (keys[KeyEvent.VK_SPACE] && shotsFired.playerCanShoot()) {
            // canShoot flag prevents user from shooting infinite bullets one after another
            shotsFired.setPlayerShot(new Bullet(ship.getPos(), 556, Bullet.UP));

            // play music
            try {
                SoundMan.play("playerShoot");
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
	}

    // called to see if game is still in play
    public boolean stillPlaying(){
        if (ship.getLives() <= 0){ // check if player is still alive
            playing = false;
        }
        if (enemies.reachedBottom()){
            playing = false;
        }
        return playing;
    }

    public boolean isPaused(){
        return paused;
    }

    public boolean doRestartGame(){
        return restartGame;
    }

    public void restartAcknowledged(){
        restartGame = false;
    }

	public void keyTyped(KeyEvent e){} // keyboard listeners
	public void keyPressed(KeyEvent e){ keys[e.getKeyCode()] = true; }
	public void keyReleased(KeyEvent e){
	    keys[e.getKeyCode()] = false;

        // pause game
        if (e.getKeyCode() == KeyEvent.VK_P && !enemies.reachedBottom()){
            paused = !paused;
        }

        // restart game when player has lost
        if (e.getKeyCode() == KeyEvent.VK_SPACE && enemies.reachedBottom()){
            restartGame = true;
        }
	}

	public void paintComponent(Graphics g){ // paints all elements of screen
		if (playing){ // while game is still ongoing
			g.drawImage(back,0,0,this);
			shield.draw(g);
			ship.draw(g);
            shotsFired.draw(g);
	       	enemies.draw(g);

		}
		else { // when game is over
			g.drawImage(end,0,0,this);
		}

		scoreMan.draw(g);

        if (paused){
            g.drawImage(pOverlay,0,0,this);
        }
	}

}
