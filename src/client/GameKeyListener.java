package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

///// JPANEL CLASS (DRAWS GRAPHICS, LISTENS FOR KEY INPUT, CALLS FOR MOVES)
// ^^^ Technically true, but all this logic should be implemented on server and only graphics drawing left here
public class GameKeyListener extends JPanel implements KeyListener {

    private final PlayerCannon playerCannon; // game elements generated in main class
    private final Map<Integer, PlayerCannon> otherPlayerCannons;
    private final AlienMan enemies;
    private Bullet shot; // bullet shot by user
    private final Scorekeeper scoreMan;
    private final Shield shield;
    private final BulletMan shotsFired;

    private final boolean[] keys; // holds keyboard input

    private boolean playing = true; // flag to see if game is still ongoing
    private boolean paused = false; // flag for when user pauses/unpauses game
    private boolean restartGame = false; // flag for when user wants to restart the game

    private final Color pOverlay = new Color(0, 0, 0, 200);
    private final File ttf = new File("fonts/visitor.ttf"); // font used to draw score, etc.
    private final Font fontL = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont(Font.PLAIN, 150); // various font sizes
    private final Font fontM = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont(Font.PLAIN, 100);
    private final Font fontS = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont(Font.PLAIN, 40);

    private final Client client;

    public GameKeyListener(PlayerCannon player, AlienMan badGuys, Scorekeeper getScore, Shield getShield, BulletMan getShots, Client client) throws IOException, FontFormatException {
        super();
        keys = new boolean[KeyEvent.KEY_LAST + 1];
        playerCannon = player;
        otherPlayerCannons = new ConcurrentHashMap<>();
        enemies = badGuys;
        scoreMan = getScore;
        shield = getShield;
        shotsFired = getShots;
        this.client = client;

        setPreferredSize(new Dimension(770, 652));
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    } // keyboard listeners

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;

        // pause game
        if (e.getKeyCode() == KeyEvent.VK_P && playing) {
            paused = !paused;
        }

        // restart game when player has lost
        if (e.getKeyCode() == KeyEvent.VK_P && !playing) {
            restartGame = true;
        }
    }

    public void move() { // takes in keyboard input and moves user cannon
        if (keys[KeyEvent.VK_RIGHT]) {
            playerCannon.right();
        }
        if (keys[KeyEvent.VK_LEFT]) {
            playerCannon.left();
        }
        if (keys[KeyEvent.VK_SPACE] && shotsFired.playerCanShoot()) {
            // canShoot flag prevents user from shooting infinite bullets one after another
            shotsFired.setPlayerShot(new Bullet(playerCannon.getPos(), 556, Bullet.UP));
        }
    }

    public void setThisPlayerName(String playerName) {
        this.playerCannon.setName(playerName);
    }

    public void setThisPlayerPosition(int pos){
        this.playerCannon.setPos(pos);
    }

    public void addPlayerCannon(int id, String playerName) {
        otherPlayerCannons.put(id, new PlayerCannon(playerName, client));
    }

    public void removePlayerCannon(int id) {
        otherPlayerCannons.remove(id);
    }

    public void setPlayerPosition(int id, int pos) {
        otherPlayerCannons.get(id).setPos(pos);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    // draws the background
    private void backDraw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 770, 652);
        g.setColor(Color.GREEN);
        g.fillRect(0, 32, 770, 3);
        scoreMan.draw(g);
    }

    // draws the pause overlay
    private void pauseOverlay(Graphics g) {
        g.setColor(pOverlay);
        g.fillRect(0, 0, 770, 652);
        Graphics2D comp2D = (Graphics2D) g;
        g.setColor(Color.WHITE);
        comp2D.setFont(fontL);
        comp2D.drawString("PAUSED", 120, 320);
    }

    // draws text for game over screen
    private void gameOverOverlay(Graphics g) {
        Graphics2D comp2D = (Graphics2D) g;
        g.setColor(Color.WHITE);
        comp2D.setFont(fontM);
        comp2D.drawString("GAME OVER", 120, 320);
        comp2D.setFont(fontS);
        comp2D.drawString("PRESS P TO PLAY AGAIN", 130, 400);
    }

    // called to see if game is still in play
    public boolean stillPlaying() {
        if (playerCannon.getLives() <= 0) { // check if player is still alive
            playing = false;
        }
        if (enemies.reachedBottom()) {
            playing = false;
        }
        return playing;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean doRestartGame() {
        return restartGame;
    }


    public void paintComponent(Graphics g) { // paints all elements of screen
        backDraw(g);
        if (playing) { // while game is still ongoing
            shield.draw(g);
            playerCannon.draw(g);
            for (PlayerCannon cannon : otherPlayerCannons.values()) {
                cannon.draw(g);
            }
            shotsFired.draw(g);
            enemies.draw(g);
        } else { // when game is over
            gameOverOverlay(g);
        }

        if (paused) {
            pauseOverlay(g);
        }
    }

}
