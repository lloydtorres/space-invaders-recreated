package client.entities.enemy.types;

import javax.swing.*;
import java.awt.*;

public class EnemyTypeC implements Enemy{
    private final Image firstPose = new ImageIcon("sprites/c1.png").getImage();
    private final Image secondPose = new ImageIcon("sprites/c2.png").getImage();
    private final int size = 37;

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
