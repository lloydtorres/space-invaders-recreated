package client.entities.enemy.types;

import javax.swing.*;
import java.awt.*;

public class EnemyTypeD implements Enemy{
    private final Image firstPose = new ImageIcon("sprites/d.png").getImage();
    private final Image secondPose = new ImageIcon("sprites/d.png").getImage();
    private final int size = 51;

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
