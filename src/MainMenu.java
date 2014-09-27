import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

// JPanel containing the main menu
public class MainMenu extends JPanel implements KeyListener {

    private boolean startGame = false;
    private int beatCount = 0;
    private int beat = 0;
    private Enemy[] aliens = {new Enemy(4,75,230),new Enemy(1,88,283),new Enemy(2,83,336),new Enemy(3,82,389)};

    private File ttf = new File("fonts/visitor.ttf"); // font used to draw score, etc.
    private Font fontL = Font.createFont(Font.TRUETYPE_FONT,ttf).deriveFont(Font.PLAIN,72); // various font sizes
    private Font fontM = Font.createFont(Font.TRUETYPE_FONT,ttf).deriveFont(Font.PLAIN,48);

    public MainMenu() throws IOException, FontFormatException{
        super();
        setSize(770,652);
        addKeyListener(this);
    }

    public void addNotify(){
        super.addNotify();
        requestFocus();
    }

    public boolean getStatus(){
        return startGame;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        // check if space has been pressed
        if (e.getKeyCode() == KeyEvent.VK_SPACE ){
            startGame = true;
        }
    }

    public void paintComponent(Graphics g){
        beatCount += 1;

        if (beatCount%70 == 0) { // move aliens when counter is divisible by beat modifier
            beat = 1 - beat; // flips between 1 and 0
            beatCount = 0;
        }

        g.setColor(Color.BLACK); // fill background
        g.fillRect(0,0,770,652);

        // set text
        Graphics2D comp2D = (Graphics2D)g;
        g.setColor(Color.WHITE);
        comp2D.setFont(fontL);
        comp2D.drawString("SPACE INVADERS",83,113);
        comp2D.setFont(fontM);
        comp2D.drawString("RECREATED",420,160);
        comp2D.drawString("= ??",161,256);
        comp2D.drawString("= 30",161,309);
        comp2D.drawString("= 20",161,362);
        comp2D.drawString("= 10",161,415);
        comp2D.drawString("HIGH SCORE:",342,308);
        comp2D.drawString("SAMPLE",342,362);
        comp2D.drawString("PRESS SPACE TO PLAY",111,519);

        // draw aliens
        for(int i=0;i<4;i++){
            Image tmpHolder = aliens[i].getImage(beat);
            int tmpX = aliens[i].getX();
            int tmpY = aliens[i].getY();
            g.drawImage(tmpHolder,tmpX,tmpY,null);
        }
    }
}
