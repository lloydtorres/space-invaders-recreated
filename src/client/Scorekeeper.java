package client;

import java.awt.*;
import java.io.*;

///// SCOREKEEPER CLASS (KEEPS TRACK OF AND DRAWS SCORE AND LIVES)
public class Scorekeeper{
    private long score = 0; // user score
    private PlayerCannon ship;

    private File ttf = new File("fonts/visitor.ttf"); // font used to draw score, etc.
    private Font font = Font.createFont(Font.TRUETYPE_FONT,ttf).deriveFont(Font.PLAIN,40);


    public Scorekeeper() throws IOException, FontFormatException {

    }
    public void setShip(PlayerCannon getShip) { ship = getShip; } // gets ship data from other classes

    public void add(long toAdd){
        score += toAdd;
    } // method used to add to score

    public void resetScore() { score = 0; } // resets core when user loses then restarts game


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
