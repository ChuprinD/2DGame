package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Engine.GamePanel;
import Engine.KeyHandler;
import Utils.ImageUtils;

public class Player extends Entity {

    private GamePanel gamePanel;
    private KeyHandler keyH;
    private boolean isItStaying = true;

    private final int screenX;
    private final int screenY;

    public Player(int x, int y, int speed, GamePanel gamePanel, KeyHandler keyH) {
        super(x, y, gamePanel.getTileSize()  * 2, gamePanel.getTileSize()  * 2, speed);

        this.gamePanel = gamePanel;
        this.keyH = keyH;

        this.screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
        this.screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);

        getPlayerImage();
    }

    public Player(GamePanel gamePanel, KeyHandler keyH) {
        this(gamePanel.getTileSize() * 24, gamePanel.getTileSize() * 24,5, gamePanel, keyH);
    }

    public void getPlayerImage() {
        try {
            BufferedImage moveSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/Player/Player.png"));
        
            setMoveSpriteSheet(moveSpriteSheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        if (keyH.isUpPressed()) {
            setDirection("up");
            moveUp();
        } else if (keyH.isDownPressed()) {
            setDirection("down");
            moveDown();
        } else if (keyH.isLeftPressed()) {
            setDirection("left");
            moveLeft();
        } else if (keyH.isRightPressed()) {
            setDirection("right");
            moveRight();
        }
        
        if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {
            isItStaying = false;
        } else {
            isItStaying = true;
        }

        increaseSpriteCounter();
        if (getSpriteCounter() > 8) {
            nextAnimation(6);
            setSpriteCounter(0);
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        BufferedImage spriteSheet = getMoveSpriteSheet();

        if (isItStaying) {
            switch (getDirection()) {
                case "up":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 2,
                            gamePanel.getOriginalTileSize() * 2,
                            gamePanel.getOriginalTileSize() * 2);
                    break;
                case "down":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 0,
                            gamePanel.getOriginalTileSize() * 2,
                            gamePanel.getOriginalTileSize() * 2);
                    break;
                case "left":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 1,
                            gamePanel.getOriginalTileSize() * 2,
                            gamePanel.getOriginalTileSize() * 2);
                    image = ImageUtils.flipImageHorizontally(image);
                    break;
                case "right":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 1,
                            gamePanel.getOriginalTileSize() * 2,
                            gamePanel.getOriginalTileSize() * 2);
                    break;
            }
        } else {
            switch (getDirection()) {
                case "up":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 5,
                            gamePanel.getOriginalTileSize() * 2,
                            gamePanel.getOriginalTileSize() * 2);
                    break;
                case "down":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 3,
                            gamePanel.getOriginalTileSize() * 2,
                            gamePanel.getOriginalTileSize() * 2);
                    break;
                case "left":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 4,
                            gamePanel.getOriginalTileSize() * 2,
                            gamePanel.getOriginalTileSize() * 2);
                    image = ImageUtils.flipImageHorizontally(image);
                    break;
                case "right":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 4,
                            gamePanel.getOriginalTileSize() * 2,
                            gamePanel.getOriginalTileSize() * 2);
                    break;
            }
        }
        g2.drawImage(image, screenX, screenY, getWidth(), getHeight(), null);
    }
    
    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
