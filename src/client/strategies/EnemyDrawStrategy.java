package client.strategies;

import java.awt.*;

public class EnemyDrawStrategy implements DrawStrategy {
    private Image enemySprite;
    public EnemyDrawStrategy(Image enemySprite){
        this.enemySprite = enemySprite;
    }
    @Override
    public void execute(int x, int y, Graphics2D graphics2D) {
        graphics2D.drawImage(enemySprite,x,y,null);
    }
}
