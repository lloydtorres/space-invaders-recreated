package client.strategies;

import client.strategies.colorizer.Colorizer;
import client.strategies.colorizer.PinkColorizer;

import java.awt.*;

public class SpriteDrawStrategy implements DrawStrategy{
    protected final Colorizer colorizer;
    protected String spritePath;

    public SpriteDrawStrategy(Colorizer colorizer, String spritePath) {

        this.colorizer = colorizer;
        this.spritePath = spritePath;
    }

    public void execute(int x, int y, Graphics2D graphics2D) {
        Image colorized = colorizer.colorize(spritePath);
        graphics2D.drawImage(colorized,x,y,null);
    }
}
