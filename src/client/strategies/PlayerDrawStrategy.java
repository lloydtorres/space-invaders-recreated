package client.strategies;



import java.awt.*;

public class PlayerDrawStrategy implements DrawStrategy {
    private Image playerSprite;
    public PlayerDrawStrategy(Image playerSprite){
        this.playerSprite = playerSprite;
    }
    @Override
    public void execute(int x, int y, Graphics2D graphics2D) {
        graphics2D.drawImage(playerSprite,x,y,null);
    }
}
