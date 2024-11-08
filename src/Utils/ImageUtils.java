package Utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageUtils {
    public static BufferedImage flipImageHorizontally(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = flippedImage.createGraphics();
        
        g.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
        g.dispose();
        
        return flippedImage;
    }
}
