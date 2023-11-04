package client.entities.enemy.types;

import javax.swing.*;
import java.awt.*;

public class EnemyTypeB implements Enemy{
    private final Image firstPose = new ImageIcon("sprites/b1.png").getImage();
    private final Image secondPose = new ImageIcon("sprites/b2.png").getImage();
    private final int size = 33;

    @Override
    public Image getFirstPose() {
        return firstPose;
    }

    @Override
    public Image getSecondPose() {
        return secondPose;
    }

    @Override
    public int getSize() {
        return size;
    }
}
