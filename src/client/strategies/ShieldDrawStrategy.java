package client.strategies;

import java.awt.*;

public class ShieldDrawStrategy implements DrawStrategy {

    @Override
    public void execute(int x, int y, Graphics2D graphics2D) {
        graphics2D.setColor(new Color(0, 255, 0));
        graphics2D.fillRect(x, y, 10, 10);
    }
}
