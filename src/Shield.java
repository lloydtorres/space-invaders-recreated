import java.awt.*;
import java.util.ArrayList;

// SHIELD CLASS - GENERATES BARRIERS ON A PIXEL-BY-PIXEL BASIS
public class Shield{
    private ArrayList<Rectangle> shieldBlocks = new ArrayList<Rectangle>(); // list of all shield pieces

    public Shield(){
        for (int x=20;x<35;x++){ // shield 1
            for (int y=0;y<10;y++){
                if (!(x >= 24 && x <= 30 && y >= 4)){ // cuts out a notch on the shield
                    shieldBlocks.add(new Rectangle(x * 5, 453 + y * 5, 5, 5));
                }
            }
        }
        for (int x=50;x<65;x++){ // shield 2
            for (int y=0;y<10;y++){
                if (!(x >= 54 && x <= 60 && y >= 4)){ // cuts out a notch on the shield
                    shieldBlocks.add(new Rectangle(x * 5, 453 + y * 5, 5, 5));
                }
            }
        }
        for (int x=85;x<100;x++){ // shield 3
            for (int y=0;y<10;y++){
                if (!(x >= 89 && x <= 95 && y >= 4)) { // cuts out a notch on the shield
                    shieldBlocks.add(new Rectangle(x * 5, 453 + y * 5, 5, 5));
                }
            }
        }
        for (int x=115;x<130;x++){ // shield 4
            for (int y=0;y<10;y++){
                if (!(x >= 119 && x <= 125 && y >=4)){ // cuts out a notch on the shield
                    shieldBlocks.add(new Rectangle(x * 5, 453 + y * 5, 5, 5));
                }
            }
        }
    }

    public boolean collide(Rectangle target){ // sees if all blocks collide with a specified target
        boolean shieldHit = false;
        for (int i=0;i< shieldBlocks.size();i++){
            if (shieldBlocks.get(i) != null){
                if (shieldBlocks.get(i).intersects(target)){
                    shieldBlocks.set(i,null);
                    shieldHit = true;
                }
            }
        }
        return shieldHit;
    }

    public void draw(Graphics g){ // draws each block
        g.setColor(new Color(0, 255, 0));
        for (Rectangle shieldBlok : shieldBlocks) {
            if (shieldBlok != null) {
                int tempX = (int) (shieldBlok.getX());
                int tempY = (int) (shieldBlok.getY());
                g.fillRect(tempX, tempY, 5, 5);
            }
        }
    }
}
