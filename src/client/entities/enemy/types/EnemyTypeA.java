package client.entities.enemy.types;

import javax.swing.*;
import java.awt.*;

public class EnemyTypeA implements Enemy{
    private final Image firstPose = new ImageIcon("sprites/a1.png").getImage();
    private final Image secondPose = new ImageIcon("sprites/a2.png").getImage();
    private final int size = 24;

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
