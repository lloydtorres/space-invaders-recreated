package client.strategies.colorizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WhiteColorizer implements Colorizer{
    private final Color purpleColor = new Color(255, 255, 255);
    private final Color colorToChange;
    private final int tolerance = 50;

    public WhiteColorizer(Color colorToChange)
    {
        this.colorToChange = colorToChange;
    }

    @Override
    public Image colorize(String imagePath) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));

            BufferedImage recoloredImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

            for (int x = 0; x < originalImage.getWidth(); x++) {
                for (int y = 0; y < originalImage.getHeight(); y++) {
                    int rgb = originalImage.getRGB(x, y);
                    Color pixelColor = new Color(rgb);

                    if (needsColoring(pixelColor)) {
                        recoloredImage.setRGB(x, y, purpleColor.getRGB());
                    } else {
                        recoloredImage.setRGB(x, y, rgb);
                    }
                }
            }

            return recoloredImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean needsColoring(Color color) {
        int redDiff = Math.abs(color.getRed() - colorToChange.getRed());
        int greenDiff = Math.abs(color.getGreen() - colorToChange.getGreen());
        int blueDiff = Math.abs(color.getBlue() - colorToChange.getBlue());

        return color.getAlpha() > 0 && redDiff <= tolerance && greenDiff <= tolerance && blueDiff <= tolerance;
    }
}
