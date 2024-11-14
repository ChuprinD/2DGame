package Assets.Object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Chest extends SuperObject {
    private BufferedImage image_opened;
    private BufferedImage image_closed;
    private BufferedImage[] openAnimationFrames;

    private int animationIndex = 0;
    private int animationTick = 0;
    private int animationSpeed = 10;

    private boolean isOpen = false;

    public Chest() {
        super("chest");

        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/resources/Outdoor decoration/Destructible Objects Sprite Sheet.png"));
            openAnimationFrames = new BufferedImage[4];

            for (int i = 0; i < openAnimationFrames.length; i++) {
                openAnimationFrames[i] = image.getSubimage(3 * 32, (i + 4) * 32, 32, 32);
            }

            image_opened = openAnimationFrames[3];
            image_closed = openAnimationFrames[0];  
            setImage(image_closed);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setCollision(true);
    }
        
    public void openChest() {
        isOpen = true;
    }

    public void closeChest() {
        isOpen = false;
    }

    @Override
    public void update() {
        if (isOpen && animationIndex < openAnimationFrames.length - 1) {
            animationTick++;
            if (animationTick >= animationSpeed) {
                animationTick = 0;
                animationIndex++;
                setImage(openAnimationFrames[animationIndex]);
            }
        }
    }
}
