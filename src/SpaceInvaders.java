/*
========================================
Space Invaders Recreated
by Lloyd Torres

Published on February 2013
Updated on July 2014

Space Invaders is copyrighted by Taito Corporation.
Code provided by Lloyd on the GNU GPL v3.0 license.
========================================
Legal:

Copyright (C) 2014 Lloyd Torres

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
========================================
*/

///// MODULES TO IMPORT
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.*;
import java.lang.*; 

///// MAIN CLASS
public class SpaceInvaders extends JFrame implements ActionListener{

	javax.swing.Timer myTimer;
	Drawer artist; // component classes of the game
	Cannon player;
	AlienTrackr enemies;
	Scorekeeper scoreMan;
	Shield shield;
	int wave = 0; // # of wins by the user, keeps track of subsequent alien start location
	
	public SpaceInvaders() throws IOException, FontFormatException{
		super("Space Invaders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(null);
		setSize(770,652);
		
		player = new Cannon();
		shield = new Shield();
		scoreMan = new Scorekeeper(player);
		enemies = new AlienTrackr(wave,scoreMan,player,shield);
		artist = new Drawer(player,enemies,scoreMan,shield);
		add(artist);

		myTimer = new javax.swing.Timer(10,this); // update every 10 ms
		myTimer.start();
		
		setResizable(false);
		setVisible(true);
	}
	
	public void resetGame(){ // called every time user wins (all aliens destroyed), resets game setup
		if (wave < 10){
			wave += 1;
		}
		player.addLife();
		enemies = new AlienTrackr(wave,scoreMan,player,shield);
		artist = new Drawer(player,enemies,scoreMan,shield);
		add(artist);
	}
	
	public void actionPerformed(ActionEvent evt){ // event listener stuff, update classes every 10 ms
		Object source = evt.getSource();
		if(source == myTimer){
			artist.move();
			artist.trackBullet();
			enemies.metronome();
	        enemies.attack();
			artist.repaint();
			if (enemies.aliensGone()){
				resetGame();
			}
		}			
	}
	
	public static void main(String[]args) throws IOException, FontFormatException{
		new SpaceInvaders();
	}
}


///// JPANEL CLASS (DRAWS GRAPHICS, LISTENS FOR KEY INPUT)
class Drawer extends JPanel implements KeyListener{
	
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

// SHIELD CLASS - GENERATES BARRIERS ON A PIXEL-BY-PIXEL BASIS
class Shield{
	private ArrayList<Rectangle> shieldBloks = new ArrayList<Rectangle>(); // list of all shield pieces
	
	public Shield(){
		for (int x=20;x<35;x++){ // shield 1
			for (int y=0;y<10;y++){
                if (!(x >= 24 && x <= 30 && y >= 4)){ // cuts out a notch on the shield
                    shieldBloks.add(new Rectangle(x*5,453+y*5,5,5));
                }
			}
		}
		for (int x=50;x<65;x++){ // shield 2
			for (int y=0;y<10;y++){
                if (!(x >= 54 && x <= 60 && y >= 4)){ // cuts out a notch on the shield
                    shieldBloks.add(new Rectangle(x*5,453+y*5,5,5));
                }
			}
		}
		for (int x=85;x<100;x++){ // shield 3
			for (int y=0;y<10;y++){
                if (!(x >= 89 && x <= 95 && y >= 4)) { // cuts out a notch on the shield
                    shieldBloks.add(new Rectangle(x * 5, 453 + y * 5, 5, 5));
                }
			}
		}
		for (int x=115;x<130;x++){ // shield 4
			for (int y=0;y<10;y++){
                if (!(x >= 119 && x <= 125 && y >=4)){ // cuts out a notch on the shield
                    shieldBloks.add(new Rectangle(x*5,453+y*5,5,5));
                }
			}
		}
	}
	
	public boolean collide(Rectangle target){ // sees if all blocks collide with a specified target
		boolean shieldHit = false;
		for (int i=0;i<shieldBloks.size();i++){
			if (shieldBloks.get(i) != null){
				if (shieldBloks.get(i).intersects(target)){
					shieldBloks.set(i,null);
					shieldHit = true;
				}
			}
		}
		return shieldHit;
	}
	
	public void draw(Graphics g){ // draws each block
		g.setColor(new Color(0, 255, 0));
        for (Rectangle shieldBlok : shieldBloks) {
            if (shieldBlok != null) {
                int tempX = (int) (shieldBlok.getX());
                int tempY = (int) (shieldBlok.getY());
                g.fillRect(tempX, tempY, 5, 5);
            }
        }
	}
}

///// SCOREKEEPER CLASS (KEEPS TRACK OF AND DRAWS SCORE AND LIVES)
class Scorekeeper{
	private long score; // user score
	private Cannon ship;
	private File ttf = new File("fonts/visitor.ttf"); // font used to draw score, etc.
	private Font font = Font.createFont(Font.TRUETYPE_FONT,ttf).deriveFont(Font.PLAIN,40);
	
	public Scorekeeper(Cannon getShip) throws IOException, FontFormatException{
		score = 0;
		ship = getShip;
    }
	
	public void add(long toAdd){
		score += toAdd;
	} // method used to add to score
	
	public void draw(Graphics g){ // draws score, user lives on screen
		Graphics2D comp2D = (Graphics2D)g;
		comp2D.setColor(new Color(255,255,255));
		comp2D.setFont(font);
		
		String pointString = "" + score;
		if (pointString.length() > 18){ // prevents score from overflowing on screen
			pointString = "999999999999999999";
		}
		comp2D.drawString(pointString,150,26);
		comp2D.drawString(""+ship.getLives(),683,26);
	}
}

///// CANNON CLASS (CONTROLS AND DRAWS USER'S CANNON)
class Cannon{
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

///// BULLET CLASS (CONTROLS AND DRAWS USER'S BULLETS)
class Bullet{
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

///// ALIEN TRACKER CLASS (CONTROLS MOVEMENT OF ALIENS)
class AlienTrackr{
	public static final int LEFT = -1; // used to specify if aliens are going left or right
	public static final int RIGHT = 1;

    private Cannon ship; // elements from main class
    private Shield shield;
	
	private Enemy aliens[][] = new Enemy[5][11]; // 2D array keeping track of each individual alien
    private Enemy ufo; // used to keep track of random UFO that shows up on top of screen
    private ArrayList<Bullet> enemyShots = new ArrayList<Bullet>(); // list of bullets shot by aliens
	private int noEnemies = 55; // number of eneimes remaining

	private int beat = 0; // flips between 0 and 1, used to determine which image to display
	private int beatCount = 0; // counter used to determine when to move aliens
	private int beatModifier; // used to mod beatCount; if divisible, can move; goes down as game goes on

	private int globDir = RIGHT; // determines direction of aliens as they move across screen
	private int topLeft = 127; // determines alien position
	private int bottomRight = 643;

	private boolean loseGame = false; // keeps track of whether or not user has lost yet
	private Scorekeeper score;
	private Random coin = new Random(); // used for chance events

	public AlienTrackr(int wave,Scorekeeper getScore,Cannon getShip,Shield getShield){
		ship = getShip;
		score = getScore;
		shield = getShield;
		beatModifier = 75 - wave; // modified depending on how many times user has won
		int waveMod = wave * 24;
		for(int i=0;i<11;i++){ // generates 2D array of aliens of each type (except for random UFO)
			aliens[0][i] = new Enemy(1,138+47*i,79+waveMod);	
		}
		for(int i=0;i<11;i++){
			for(int j=1;j<3;j++){
				aliens[j][i] = new Enemy(2,133+47*i,79+34*j+waveMod);
			}
		}
		for(int i=0;i<11;i++){
			for(int j=3;j<5;j++){
				aliens[j][i] = new Enemy(3,131+47*i,79+34*j+waveMod);
			}
		}
	}
	
	public boolean collide(Rectangle bullet){ // checks if alien hits a bullet from user
		boolean toReturn = false; // flag to determine when alien has been hit
		boolean ufoHit = false; // flag to determine if random UFO has been hit

		if (ufo != null){ // checks if random UFO is hit
			Rectangle rectCheck = ufo.getRect();
			toReturn = rectCheck.intersects(bullet);
			if (toReturn){ // if UFO was hit
				score.add(ufo.getScore());
				ufo = null;
				ufoHit = true;
			}
		}
		
		if (!ufoHit){ // otherwise, check the other aliens
			for (int i=0;i<11;i++){
				for (int j=0;j<5;j++){
					if (aliens[j][i] != null){
						Rectangle rectCheck = aliens[j][i].getRect();	
						toReturn = rectCheck.intersects(bullet);	
						if (toReturn){ // if hit, remove alien and add score, etc.
							score.add(aliens[j][i].getScore());
							aliens[j][i] = null;
							beatModifier = Math.max(1, beatModifier - 1); // make aliens move faster
							noEnemies -= 1;
							break;
						}
					}
				}
				if (toReturn){ // stop looking for aliens
					break;
				}
			}
		}
		
		return toReturn;
	}
	
	public boolean lostYet(){ // returns flag determining if user lost
		return loseGame;
	}
	
	public boolean aliensGone(){ // checks if all aliens are gone from map
        return noEnemies == 0 && ufo == null;
	}

	public void ufoTrack(){ // used to generate and move random UFO
		if (coin.nextInt(500) == 0 && ufo == null){ // generate new UFO
			ufo = new Enemy(4,770,45);
		}
		if (ufo != null && beatCount%2==0){ // move UFO
			ufo.smoothLeft();
		}
		if (ufo != null && ufo.getX() <= -51){ // if UFO is out of bounds
			ufo = null;
		}
	}
	
	private void downer(){ // brings down aliens on screen
        if (!loseGame) {
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 5; j++) {
                    if (aliens[j][i] != null) {
                        aliens[j][i].down();
                        shield.collide(aliens[j][i].getRect()); // see if aliens are hitting shield blocks
                        if (aliens[j][i].getY() >= 551) { // if aliens are out of bounds, game over
                            loseGame = true;
                            break;
                        }
                    }
                }
            }
            beatModifier = Math.max(1, beatModifier - 1);
        }
	}
	
	public void move(){
        if (!loseGame) { // stop moving if game over
            int spaceTest;
            if (globDir == LEFT) { // while aliens are going to left
                spaceTest = hEdgeCheck(0, RIGHT);
                if (topLeft + 47 * spaceTest - 18 > 30) { // if leftmost alien still has not hit bounds, keep moving left
                    topLeft -= 18;
                    bottomRight -= 18;
                    for (int i = 0; i < 11; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (aliens[j][i] != null) {
                                aliens[j][i].left();
                                shield.collide(aliens[j][i].getRect());
                            }
                        }
                    }
                }
                else { // otherwise go down and start moving right
                    downer();
                    globDir = RIGHT;
                }
            }
            else { // while aliens are going to right
                spaceTest = hEdgeCheck(10, LEFT);
                if (bottomRight - 47 * (10 - spaceTest) + 18 < 750) { // see if rightmost alien has hit bounds
                    topLeft += 18;
                    bottomRight += 18;
                    for (int i = 0; i < 11; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (aliens[j][i] != null) {
                                aliens[j][i].right();
                                shield.collide(aliens[j][i].getRect());
                            }
                        }
                    }
                }
                else { // otherwise go down and start moving left
                    downer();
                    globDir = LEFT;
                }
            }
        }
	}
	
	private int hEdgeCheck(int lvl,int dir){ // recursive method that returns the leftmost or rightmost "active" column (one that still has aliens on it)
		if (lvl < 11 && lvl >= 0){ // limits bounds
			boolean deadColumn = true; // flag determining if column still has active aliens
			for(int i=0;i<5;i++){ // check if aliens still active in column
				if(aliens[i][lvl]!=null){
					deadColumn = false;
					break;
				}
			}
			if(deadColumn){ // if column is dead, check next one
				return hEdgeCheck(lvl+dir,dir);
			}
			else{ // otherwise if column is alive, this must be the left or rightmost active column
				return lvl;
			}
		}
		else{ // if it goes out of bounds
			if (dir == LEFT){ // the most extreme values must be the left or rightmost active columns
				return 0;
			}
			else{
				return 11;
			}
		}
	}
	
	public void metronome(){ // function manages "beats", i.e. alien move speed, which image to display, etc.
		beatCount += 1;
		if (beatCount%beatModifier == 0){ // move aliens when counter is divisible by beat modifier
			move();
			beat = 1 - beat; // flips between 1 and 0
			beatCount = 0;
		}
		for (int i=0;i<enemyShots.size();i++){ // move enemy shots
			if (enemyShots.get(i) != null){
				enemyShots.get(i).move();
				boolean hitTest = false; // sees if alien bullets have hit shield
				hitTest = shield.collide(enemyShots.get(i).getRect());
				if (hitTest){
					enemyShots.set(i,null);
				}
			}
		}
	}
	
	public void attack(){ // used to generate bullets from aliens
		boolean alreadyShot = false; // flag to determine if aliens have shot on that beat, prevents aliens from attacking all at once
		for (int i=0;i<11;i++){
			for (int j=4;j>=0;j--){
				if (aliens[j][i] != null){ // for each alien
					int ranNoGen = coin.nextInt((int)(((noEnemies+1)/56.0)*1500)); // random number generator more likely to hit 0 when less aliens are present
					if (ranNoGen == 0){
						alreadyShot = true;
						int newX = aliens[j][i].getX() + (int)(aliens[j][i].getSizeX()/2); // generate new bullet from specific alien
						int newY = aliens[j][i].getY() + 12;
						Bullet tempShot = new Bullet(newX,newY,Bullet.DOWN);
						enemyShots.add(tempShot);
					}
					break;
				}
			}
			if (alreadyShot){ // stop searching when bullet has been fired
				break;
			}
		}

		ship.collide(enemyShots); // check bullet collisions and see if ship is still alive
		if (ship.getLives() <= 0){
			loseGame = true;
		}

        // this implementation prevents a ConcurrentModificationException
		for (int k=0;k<enemyShots.size();k++){ // gets rid of bullets outside of screen
			if (enemyShots.get(k)!=null && enemyShots.get(k).getY() > 770){
					enemyShots.set(k,null);
			}
		}
		while (enemyShots.contains(null)){ // clears out non-existent bullets
			enemyShots.remove(null);
		}
	}
	
	public ArrayList<Bullet> getEnemyShots(){
		return enemyShots;
	}
	
	public void draw(Graphics g){ // draws aliens, bullets, random UFO
		for(int i=0;i<11;i++){
			for (int j=0;j<5;j++){
				if (aliens[j][i] != null){
					Image tmpHolder = aliens[j][i].getImage(beat);
					int tmpX = aliens[j][i].getX();
					int tmpY = aliens[j][i].getY();
					g.drawImage(tmpHolder,tmpX,tmpY,null);
				}
			}
		}
		for (Bullet shot : enemyShots){
			shot.draw(g);
		}
		if (ufo != null){
			Image tmpHolder = ufo.getImage(beat);
			int tmpX = ufo.getX();
			int tmpY = ufo.getY();
			g.drawImage(tmpHolder,tmpX,tmpY,null);
		}
	}
}

///// ENEMY CLASS (KEEPS TRACK OF EACH INDIVIDUAL ALIEN)
class Enemy{
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