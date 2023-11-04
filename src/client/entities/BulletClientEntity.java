package client.entities;

import client.entities.common.ClientEntity;
import client.utilities.Drawer;

public class BulletClientEntity extends ClientEntity {
    private int direction;

    public BulletClientEntity(int id, Drawer drawer, int x, int y){
        super(id, drawer, x, y);
    }
}
