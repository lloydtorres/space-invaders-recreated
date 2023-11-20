package client;

import client.strategies.DrawStrategy;
import common.EntityType;

import javax.swing.text.html.parser.Entity;
import java.awt.*;

public class ClientEntity {
    private int id;
    private int x, y;
    private DrawStrategy drawStrategy;

    public ClientEntity(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void setDrawStrategy(DrawStrategy drawStrategy) {
        this.drawStrategy = drawStrategy;
    }

    public void draw(Graphics2D graphics2D) {
        if(drawStrategy != null){
            drawStrategy.execute(x, y, graphics2D);
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
