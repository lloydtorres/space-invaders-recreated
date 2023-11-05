package client.entities.common;

import client.utilities.Drawer;

import java.awt.*;

public class ClientEntity {
    protected int id;
    protected Rectangle rectangle;
    protected int x, y;

    public ClientEntity(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Rectangle getRect() {return this.rectangle;}
}
