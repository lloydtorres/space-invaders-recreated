import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

///// ALIEN TRACKER CLASS (CONTROLS MOVEMENT OF ALIENS)
public class AlienMan {
	public static final int LEFT = -1; // used to specify if aliens are going left or right
	public static final int RIGHT = 1;

    private Cannon ship; // elements from main class
    private Shield shield;

	private Enemy aliens[][] = new Enemy[5][11]; // 2D array keeping track of each individual alien
    private Enemy ufo; // used to keep track of random UFO that shows up on top of screen
    private Clip ufoMusic; // used to hold and stop UFO music when playing
    private ArrayList<Bullet> enemyShots = new ArrayList<Bullet>(); // list of bullets shot by aliens
	private int noEnemies = 55; // number of enemies remaining

	private int beat = 0; // flips between 0 and 1, used to determine which image to display
	private int beatCount = 0; // counter used to determine when to move aliens
	private int beatModifier; // used to mod beatCount; if divisible, can move; goes down as game goes on

    private int musicCount = 1; // used to determine which WAV file to play per beat

	private int globDir = RIGHT; // determines direction of aliens as they move across screen
	private int topLeft = 127; // determines alien position
	private int bottomRight = 643;

	private boolean loseGame = false; // keeps track of whether or not user has lost yet
	private Scorekeeper score;
	private Random coin = new Random(); // used for chance events

	public AlienMan(int wave, Scorekeeper getScore, Cannon getShip, Shield getShield){
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

                            // play music
                            try {
                                SoundMan.play("alienShot");
                            } catch (UnsupportedAudioFileException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (LineUnavailableException e) {
                                e.printStackTrace();
                            }

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

	public boolean reachedBottom(){ // returns flag determining if user lost
		return loseGame;
	}

	public boolean aliensGone(){ // checks if all aliens are gone from map
        return noEnemies == 0 && ufo == null;
	}

	public void ufoTrack(){ // used to generate and move random UFO
		if (coin.nextInt(2000) == 0 && ufo == null){ // generate new UFO
			ufo = new Enemy(4,770,45);

            // play music
            try {
                ufoMusic = SoundMan.play("ufo");
                ufoMusic.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
		if (ufo != null && beatCount%2==0){ // move UFO
			ufo.smoothLeft();
		}
		if (ufo != null && ufo.getX() <= -51){ // if UFO is out of bounds
			ufo = null;
		}

        // stop music
        if (ufo == null && ufoMusic != null){
            ufoMusic.stop();
            ufoMusic = null;
        }
	}

    public void ufoDestroy(){
        if (ufo != null && ufoMusic != null){
            ufo = null;
            ufoMusic.stop();
            ufoMusic = null;
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

	public boolean metronome(){ // function manages "beats", i.e. alien move speed, which image to display, etc.
		beatCount += 1;

		if (beatCount%beatModifier == 0){ // move aliens when counter is divisible by beat modifier
			move();
			beat = 1 - beat; // flips between 1 and 0
			beatCount = 0;

            // play music
            try {
                SoundMan.play(Integer.toString(musicCount));
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

            musicCount += 1;
            if (musicCount > 4){
                musicCount = 1;
            }

            return true;
        }
        return false;
	}

	public ArrayList<Bullet> attack(){ // used to generate bullets from aliens
		boolean alreadyShot = false; // flag to determine if aliens have shot on that call, prevents aliens from attacking all at once
		for (int i=0;i<11;i++){
			for (int j=4;j>=0;j--){
				if (aliens[j][i] != null){ // for each alien
					int ranNoGen = coin.nextInt((int)(((noEnemies+1)/56.0)*100)); // random number generator more likely to hit 0 when less aliens are present
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

		if (ufo != null){
			Image tmpHolder = ufo.getImage(beat);
			int tmpX = ufo.getX();
			int tmpY = ufo.getY();
			g.drawImage(tmpHolder,tmpX,tmpY,null);
		}
	}
}
