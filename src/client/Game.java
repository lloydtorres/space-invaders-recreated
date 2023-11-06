package client;

import client.strategies.*;
import client.strategies.colorizer.*;
import common.EntityType;
import common.packets.IPacketFactory;
import common.packets.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

///// JPANEL CLASS (DRAWS GRAPHICS, LISTENS FOR KEY INPUT, CALLS FOR MOVES)
// ^^^ Technically true, but all this logic should be implemented on server and only graphics drawing left here
public class Game extends JPanel implements KeyListener, Runnable {

    private final boolean[] keys; // holds keyboard input
    private final File ttf = new File("fonts/visitor.ttf"); // font used to draw score, etc.
//    private final Font fontL = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont(Font.PLAIN, 150); // various font sizes
//    private final Font fontM = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont(Font.PLAIN, 100);
//    private final Font fontS = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont(Font.PLAIN, 40);
    private final Client client;
    private final IPacketFactory packetFactory;
    private final BulletDrawStrategy bulletDrawStrategy = new BulletDrawStrategy();
    private final ShieldDrawStrategy shieldDrawStrategy = new ShieldDrawStrategy();
    private final Color originalColor = new Color(0, 255, 0);
    private final Colorizer[] playerColorizers = {new WhiteColorizer(originalColor), new BlueColorizer(originalColor), new PinkColorizer(originalColor), new PurpleColorizer(originalColor), new YellowColorizer(originalColor)};
    private final EnemyDrawStrategy enemyDrawStrategy = new EnemyDrawStrategy(playerColorizers[0], "sprites/c11.png");
    private final Font scoreFont = Font.createFont(Font.TRUETYPE_FONT,ttf).deriveFont(Font.PLAIN,40);
    private final Map<Integer, ClientEntity> entities;
    private boolean isRunning;
    private long lastFrameTime;
    private double timeStep;
    private int score = 0;
    private int livesLeft = 0;
    public Game(Client client, IPacketFactory packetFactory, int refreshRate) throws IOException, FontFormatException {
        super();
        keys = new boolean[KeyEvent.KEY_LAST + 1];
        this.client = client;
        this.packetFactory = packetFactory;
        entities = new ConcurrentHashMap<>();
        isRunning = true;
        setPreferredSize(new Dimension(770, 652));
        addKeyListener(this);
        lastFrameTime = System.currentTimeMillis();
        timeStep = 1000.0 / refreshRate;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawBackground(g2d);
        drawEntities(g2d);
        drawLivesLeft(g2d);
        drawScore(g2d);
    }

    public void loop(){
        while(isRunning){
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - lastFrameTime;
            if (deltaTime < timeStep) {
                continue;
            }
            sendInputData();
            repaint();
            lastFrameTime = currentTime;
        }
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    private void drawBackground(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, 770, 652);
        graphics.setColor(Color.GREEN);
        graphics.fillRect(0, 32, 770, 3);
    }

    private void drawEntities(Graphics2D graphics){
        for (ClientEntity entity : entities.values()){
            entity.draw(graphics);
        }
    }
    private void drawLivesLeft(Graphics2D graphics){
        graphics.setColor(Color.WHITE);
        graphics.setFont(scoreFont);
        graphics.drawString("LIVES x " + livesLeft,487,26);
        return;
    }
    private void drawScore(Graphics2D graphics){
        graphics.setColor(Color.WHITE);
        graphics.setFont(scoreFont);

        String pointString = "SCORE: " + score;
        if (pointString.length() > 19){ // prevents score from overflowing on screen
            pointString = "SCORE: inf";
        }
        graphics.drawString(pointString,27,26);
        return;
    }
    public void updateScore(int newScore){
        score = newScore;
    }

    public void updateLivesLeft(int newLivesLeft){
        livesLeft = newLivesLeft;
    }

    public void updateEntity(int id, EntityType entityType, int x, int y){
        ClientEntity clientEntity = entities.get(id);
        if(clientEntity != null){
            clientEntity.setX(x);
            clientEntity.setY(y);
            return;
        }
        //if client entity was not found
        clientEntity = new ClientEntity(id, x, y);
        DrawStrategy drawStrategy = null;
        switch (entityType){
            case PLAYER:
                drawStrategy = new PlayerDrawStrategy(playerColorizers[id % 5], "sprites/cannon.png");;
                break;
            case SHIELD:
                drawStrategy = shieldDrawStrategy;
                break;
            case BULLET:
                drawStrategy = bulletDrawStrategy;
                break;
            case ENEMY:
                drawStrategy = enemyDrawStrategy;
                break;
        }
        clientEntity.setDrawStrategy(drawStrategy);
        entities.put(id, clientEntity);
    }
    public void removeEntity(int id){
        entities.remove(id);
    }

    private void sendInputData(){
        Packet packetToSend = packetFactory.getPacket(client.getThisPlayer().getId(), keys);
        if(packetToSend != null){
            client.sendPacket(packetToSend);
        }
    }
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void run() {
        loop();
    }
//    public void paintComponent(Graphics g) {
//    }

//    public void move() { // takes in keyboard input and moves user cannon
//        if (keys[KeyEvent.VK_RIGHT]) {
//            playerCannon.right();
//        }
//        if (keys[KeyEvent.VK_LEFT]) {
//            playerCannon.left();
//        }
//        if (keys[KeyEvent.VK_SPACE] && shotsFired.playerCanShoot()) {
//            // canShoot flag prevents user from shooting infinite bullets one after another
//            shotsFired.setPlayerShot(new Bullet(playerCannon.getPos(), 556, Bullet.UP));
//        }
//    }
//
//    public void setThisPlayerName(String playerName) {
//        this.playerCannon.setName(playerName);
//    }
//
//    public void setThisPlayerPosition(int pos){
//        this.playerCannon.setPos(pos);
//    }
//
//    public void addPlayerCannon(int id, String playerName) {
//        otherPlayerCannons.put(id, new PlayerCannon(playerName, client));
//    }
//
//    public void removePlayerCannon(int id) {
//        otherPlayerCannons.remove(id);
//    }

//    public void setPlayerPosition(int id, int pos) {
//        otherPlayerCannons.get(id).setPos(pos);
//    }



    // draws the background



    // draws the pause overlay
//    private void pauseOverlay(Graphics g) {
//        g.setColor(pOverlay);
//        g.fillRect(0, 0, 770, 652);
//        Graphics2D comp2D = (Graphics2D) g;
//        g.setColor(Color.WHITE);
//        comp2D.setFont(fontL);
//        comp2D.drawString("PAUSED", 120, 320);
//    }

    // draws text for game over screen
//    private void gameOverOverlay(Graphics g) {
//        Graphics2D comp2D = (Graphics2D) g;
//        g.setColor(Color.WHITE);
//        comp2D.setFont(fontM);
//        comp2D.drawString("GAME OVER", 120, 320);
//        comp2D.setFont(fontS);
//        comp2D.drawString("PRESS P TO PLAY AGAIN", 130, 400);
//    }

    // called to see if game is still in play
//    public boolean stillPlaying() {
//        if (playerCannon.getLives() <= 0) { // check if player is still alive
//            playing = false;
//        }
//        if (enemies.reachedBottom()) {
//            playing = false;
//        }
//        return playing;
//    }

//    public boolean isPaused() {
//        return paused;
//    }
//
//    public boolean doRestartGame() {
//        return restartGame;
//    }


//    public void paintComponent(Graphics g) { // paints all elements of screen
//        backDraw(g);
//        if (playing) { // while game is still ongoing
//            shield.draw(g);
//            playerCannon.draw(g);
//            for (PlayerCannon cannon : otherPlayerCannons.values()) {
//                cannon.draw(g);
//            }
//            shotsFired.draw(g);
//            enemies.draw(g);
//        } else { // when game is over
//            gameOverOverlay(g);
//        }
//
//        if (paused) {
//            pauseOverlay(g);
//        }
//    }

}
