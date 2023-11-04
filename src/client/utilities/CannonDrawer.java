package client.utilities;

import javax.swing.*;
import java.awt.*;

public class CannonDrawer implements Drawer{
    private final Image imgShip = new ImageIcon("sprites/cannon.png").getImage(); // picture of ship
    @Override
    public void draw(Graphics graphics, int x, int y) {
        graphics.drawString("", x - 12, 580);
//        if (!gotShot){ // normal ship
//            graphics.drawImage(imgShip,x-12,y, null);
//        }
//        // draws broken ship when hit
//        else {
//            counter++;
//            if (counter%5 == 0){
//                curImage = 1 - curImage; // flips between 1 and 0
//            }
//            if (curImage == 0){
//                graphics.drawImage(shipDown0,pos-12,570,null);
//            }
//            else{
//                graphics.drawImage(shipDown1,pos-12,570,null);
//            }
//        }
    }
}
