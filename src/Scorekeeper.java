import java.awt.*;
import java.io.*;

///// SCOREKEEPER CLASS (KEEPS TRACK OF AND DRAWS SCORE AND LIVES)
public class Scorekeeper{
	private long score; // user score
	private Cannon ship;

	private File ttf = new File("fonts/visitor.ttf"); // font used to draw score, etc.
	private Font font = Font.createFont(Font.TRUETYPE_FONT,ttf).deriveFont(Font.PLAIN,40);

    private File hiScoreOld; // file I/O
    private BufferedReader inFile;
    private File hiScoreTxt;
    private BufferedWriter outFile;

	public Scorekeeper(Cannon getShip) throws IOException, FontFormatException{
		score = 0;
		ship = getShip;
    }

	public void add(long toAdd){
		score += toAdd;
	} // method used to add to score

    // compares stored hi score value with current score; overwrites if larger
    // thanks to http://stackoverflow.com/questions/13729625/overwriting-txt-file-in-java
    public void calculateHiScore(){
        try {
            hiScoreTxt = new File("src/hiscore.txt");
            inFile = new BufferedReader(new FileReader(hiScoreTxt));
            long storedHiScore = Long.parseLong(inFile.readLine(),10);
            inFile.close();
            if (score > storedHiScore){
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
		if (pointString.length() > 12){ // prevents score from overflowing on screen
			pointString = "SCORE: 999999999999";
		}
		comp2D.drawString(pointString,27,26);
		comp2D.drawString("LIVES x "+ship.getLives(),487,26);
	}
}
