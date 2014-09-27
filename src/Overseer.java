import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
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

    private Color pOverlay = new Color(0, 0, 0, 200);
    private File ttf = new File("fonts/visitor.ttf"); // font used to draw score, etc.
    private Font fontL = Font.createFont(Font.TRUETYPE_FONT,ttf).deriveFont(Font.PLAIN,150); // various font sizes
    private Font fontM = Font.createFont(Font.TRUETYPE_FONT,ttf).deriveFont(Font.PLAIN,100);
    private Font fontS = Font.createFont(Font.TRUETYPE_FONT,ttf).deriveFont(Font.PLAIN,40);

	public Overseer(Cannon player, AlienMan badGuys, Scorekeeper getScore, Shield getShield, BulletMan getShots) throws IOException, FontFormatException{
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

    // draws the background
    private void backDraw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,770,652);
        g.setColor(Color.GREEN);
        g.fillRect(0,32,770,3);
        scoreMan.draw(g);
    }

    // draws the pause overlay
    private void pauseOverlay(Graphics g){
        g.setColor(pOverlay);
        g.fillRect(0,0,770,652);
        Graphics2D comp2D = (Graphics2D)g;
        g.setColor(Color.WHITE);
        comp2D.setFont(fontL);
        comp2D.drawString("PAUSED",120,320);
    }

    // draws text for game over screen
    private void gameOverOverlay(Graphics g){
        Graphics2D comp2D = (Graphics2D)g;
        g.setColor(Color.WHITE);
        comp2D.setFont(fontM);
        comp2D.drawString("GAME OVER",120,320);
        comp2D.setFont(fontS);
        comp2D.drawString("PRESS P TO PLAY AGAIN",130,400);
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

    @Override
	public void keyTyped(KeyEvent e){} // keyboard listeners

    @Override
	public void keyPressed(KeyEvent e){ keys[e.getKeyCode()] = true; }

    @Override
	public void keyReleased(KeyEvent e){
	    keys[e.getKeyCode()] = false;

        // pause game
        if (e.getKeyCode() == KeyEvent.VK_P && playing){
            paused = !paused;
        }

        // restart game when player has lost
        if (e.getKeyCode() == KeyEvent.VK_P && !playing){
            restartGame = true;
        }
	}

	public void paintComponent(Graphics g){ // paints all elements of screen
        backDraw(g);
		if (playing){ // while game is still ongoing
			shield.draw(g);
			ship.draw(g);
            shotsFired.draw(g);
	       	enemies.draw(g);
		}
		else { // when game is over
            gameOverOverlay(g);
		}

        if (paused){
            pauseOverlay(g);
        }
	}

}
