package client.strategies;

import client.strategies.colorizer.Colorizer;

import java.awt.*;

public class EnemyDrawStrategy extends SpriteDrawStrategy{

    public EnemyDrawStrategy(Colorizer colorizer, String spritePath) {
        super(colorizer, spritePath);
    }
}
