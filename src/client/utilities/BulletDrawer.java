package client.utilities;

import java.awt.*;

public class BulletDrawer implements Drawer{
    private final Color color = new Color(0, 255, 0);
    private final int width = 2;
    private final int height = 14;
    
    @Override
    public void draw(Graphics graphics, int x, int y) {
        graphics.setColor(color);
        graphics.fillRect(x,y,width,height);
    }
}
