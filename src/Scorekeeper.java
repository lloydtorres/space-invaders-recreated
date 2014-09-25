import java.awt.*;
import java.io.File;
import java.io.IOException;

///// SCOREKEEPER CLASS (KEEPS TRACK OF AND DRAWS SCORE AND LIVES)
public class Scorekeeper{
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
