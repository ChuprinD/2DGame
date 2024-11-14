package Assets.Object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Key extends SuperObject {
    public Key() {
        super("key");

        try{
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/resources/Outdoor decoration/Key.png"));
            setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
