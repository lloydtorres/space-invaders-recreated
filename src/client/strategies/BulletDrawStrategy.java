package client.strategies;

import client.strategies.DrawStrategy;

import java.awt.*;

public class BulletDrawStrategy implements DrawStrategy {
    @Override
    public void execute(int x, int y, Graphics2D graphics2D) {
        graphics2D.setColor(new Color(255,255,255));
        graphics2D.fillRect(x,y,2,14);
    }
}
