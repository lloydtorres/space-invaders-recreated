package client.entities.common;

import client.utilities.Drawer;

import java.awt.*;

public class DrawableClientEntity extends ClientEntity{
    protected Drawer drawer;

    public DrawableClientEntity(int id, Drawer drawer, int x, int y) {
        super(id, x, y);
        this.drawer = drawer;
    }

    @Override
    public void draw(Graphics graphics){
        drawer.draw(graphics,x, y);
    }
}
