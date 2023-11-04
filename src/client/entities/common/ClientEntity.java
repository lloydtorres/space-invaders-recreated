package client.entities.common;

import client.utilities.Drawer;

import java.awt.*;

public class ClientEntity {
    protected int id;
    protected Drawer drawer;
    protected Rectangle rectangle;
    protected int x, y;

    public ClientEntity(int id, Drawer drawer, int x, int y){
        this.id = id;
        this.drawer = drawer;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics graphics){
        drawer.draw(graphics,x, y);
    }

    public Rectangle getRect() {return this.rectangle;}
}
