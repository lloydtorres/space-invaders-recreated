import java.awt.*;
import java.io.*;

///// SCOREKEEPER CLASS (KEEPS TRACK OF AND DRAWS SCORE AND LIVES)
public class Scorekeeper{
	private long score = 0; // user score
    private long hiScore = 0; // stored hi score
	private Cannon ship;

	private File ttf = new File("fonts/visitor.ttf"); // font used to draw score, etc.
	private Font font = Font.createFont(Font.TRUETYPE_FONT,ttf).deriveFont(Font.PLAIN,40);

    private BufferedReader inFile;
    private File hiScoreTxt;
    private BufferedWriter outFile;

    public Scorekeeper() throws IOException, FontFormatException {
        hiScoreTxt = new File("src/hiscore.txt"); // get hi score
        inFile = new BufferedReader(new FileReader(hiScoreTxt));
        try{
            hiScore = Long.parseLong(inFile.readLine(),10);
        }
        catch (NumberFormatException e){
            hiScore = 0;
        }
        inFile.close();
    }

    public void setShip(Cannon getShip) { ship = getShip; } // gets ship data from other classes

    public long getHiScore() { return hiScore; }

	public void add(long toAdd){
		score += toAdd;
	} // method used to add to score

    public void resetScore() { score = 0; } // resets core when user loses then restarts game

    // compares stored hi score value with current score; overwrites if larger
    // thanks to http://stackoverflow.com/questions/13729625/overwriting-txt-file-in-java
    public void calculateHiScore(){
        try {
            if (score > hiScore){
                hiScoreTxt.delete();
                hiScoreTxt = new File("src/hiscore.txt");
                outFile = new BufferedWriter(new FileWriter(hiScoreTxt,false));
                outFile.write(String.valueOf(score));
                outFile.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

	public void draw(Graphics g){ // draws score, user lives on screen
		Graphics2D comp2D = (Graphics2D)g;
		comp2D.setColor(Color.WHITE);
		comp2D.setFont(font);

		String pointString = "SCORE: " + score;
		if (pointString.length() > 19){ // prevents score from overflowing on screen
            score = 999999999999L;
			pointString = "SCORE: 999999999999";
		}
		comp2D.drawString(pointString,27,26);
		comp2D.drawString("LIVES x "+ship.getLives(),487,26);
	}
}
