package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;

import Engine.GamePanel;
import Engine.KeyHandler;

public class Player extends Entity {

    private GamePanel gamePanel;
    private KeyHandler keyH;
    private boolean isItStaying = true;


    public Player(int x, int y, int speed, GamePanel gamePanel, KeyHandler keyH) {
        super(x, y, gamePanel.getTileSize(), gamePanel.getTileSize(), speed);

        this.gamePanel = gamePanel;
        this.keyH = keyH;

        getPlayerImage();
    }

    public Player(GamePanel gamePanel, KeyHandler keyH) {
        super(100, 100, gamePanel.getTileSize(), gamePanel.getTileSize(), 4);

        this.gamePanel = gamePanel;
        this.keyH = keyH;

        getPlayerImage();
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
        // g2.setColor(Color.WHITE);
        // g2.fillRect(getX(), getY(), getWidth(), getWidth());

        BufferedImage image = null;
        BufferedImage spriteSheet = getMoveSpriteSheet();

        if (isItStaying) {
            switch (getDirection()) {
                case "up":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 2, gamePanel.getOriginalTileSize(),
                            gamePanel.getOriginalTileSize());
                    break;
                case "down":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 0, gamePanel.getOriginalTileSize(),
                            gamePanel.getOriginalTileSize());
                    break;
                case "left":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 3, gamePanel.getOriginalTileSize(),
                            gamePanel.getOriginalTileSize());
                    break;
                case "right":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 1, gamePanel.getOriginalTileSize(),
                            gamePanel.getOriginalTileSize());
                    break;
            }
        } else {
            switch (getDirection()) {
                case "up":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 6, gamePanel.getOriginalTileSize(),
                            gamePanel.getOriginalTileSize());
                    break;
                case "down":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 4, gamePanel.getOriginalTileSize(),
                            gamePanel.getOriginalTileSize());
                    break;
                case "left":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 7, gamePanel.getOriginalTileSize(),
                            gamePanel.getOriginalTileSize());
                    break;
                case "right":
                    image = spriteSheet.getSubimage(32 * getCurrentAnimation(), 32 * 5, gamePanel.getOriginalTileSize(),
                            gamePanel.getOriginalTileSize());
                    break;
            }
        }

        g2.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
    }
}
