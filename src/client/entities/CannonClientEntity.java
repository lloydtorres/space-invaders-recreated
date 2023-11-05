package client.entities;

import client.entities.common.ClientEntity;
import client.utilities.Drawer;

import javax.swing.*;
import java.awt.*;

public class CannonClientEntity extends ClientEntity {
    private int pos = 366; // position on screen
    private String name;
    private Image imgShip = new ImageIcon("sprites/cannon.png").getImage(); // picture of ship
    private Image shipDown0 = new ImageIcon("sprites/c_broken1.png").getImage(); // broken ships
    private Image shipDown1 = new ImageIcon("sprites/c_broken2.png").getImage();
    private int curImage = 0; // determines which broken image will be displayed
    private boolean gotShot = false; // flag to determine if user has been hit
    private int counter = 0; // used to moderate when to display image

    public CannonClientEntity(int id, int x, int y){
        super(id, x, y);
    }

}

