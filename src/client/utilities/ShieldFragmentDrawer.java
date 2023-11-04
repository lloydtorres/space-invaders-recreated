package client.utilities;

import java.awt.*;

public class ShieldFragmentDrawer implements Drawer{
    private final Color color = new Color(0, 255, 0);

    @Override
    public void draw(Graphics graphics, int x, int y) {
        graphics.setColor(color);
        graphics.fillRect(x, y,5,5);
    }
}
