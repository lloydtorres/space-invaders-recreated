package client;

import client.strategies.DrawStrategy;

import java.awt.*;

public class ClientEntity {
    private int id;
    private int x, y;
    private DrawStrategy drawStrategy;
    private ClientEntityType clientEntityType;

    public ClientEntity(int id, int x, int y, ClientEntityType clientEntityType) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.clientEntityType = clientEntityType;
    }

    public void setDrawStrategy(DrawStrategy drawStrategy) {
        this.drawStrategy = drawStrategy;
    }

    public void draw(Graphics2D graphics2D) {
        if (drawStrategy != null) {
            drawStrategy.execute(x, y, graphics2D);
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ClientEntityType getClientEntityType() {
        return clientEntityType;
    }
}
