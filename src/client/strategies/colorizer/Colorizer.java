package client.strategies.colorizer;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Colorizer {
    Image colorize(String imagePath);
}
