package Assets.Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Assets.Object.SuperObject;
import Engine.GamePanel;
import Engine.KeyHandler;
import Utils.ImageUtils;
import Assets.Object.Chest;

public class Player extends Entity {

    private GamePanel gamePanel;
    private KeyHandler keyH;
    private boolean isItStaying = true;
    private int hasKey = 0;

    private final int screenX, screenY;

    public Player(int x, int y, int speed, GamePanel gamePanel, KeyHandler keyH) {
        super(x, y, gamePanel.getTileSize() * 2, gamePanel.getTileSize() * 2, speed);
        
        Rectangle solidArea = new Rectangle(11 * gamePanel.getScale(), 12 * gamePanel.getScale(),
                9 * gamePanel.getScale(), 9 * gamePanel.getScale());

        setSolidArea(solidArea);
        setdefaultSolidArea(solidArea.x, solidArea.y);

        this.gamePanel = gamePanel;
        this.keyH = keyH;

        this.screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
        this.screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);

        getPlayerImage();
    }

    public Player(GamePanel gamePanel, KeyHandler keyH) {
        this(gamePanel.getTileSize() * 28, gamePanel.getTileSize() * 27,5, gamePanel, keyH);
    }

    public void getPlayerImage() {
        try {
            BufferedImage moveSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/Player/Player.png"));
        
            setMoveSpriteSheet(moveSpriteSheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.isUpPressed()) {
            setDirection("up");
        } else if (keyH.isDownPressed()) {
            setDirection("down");
        } else if (keyH.isLeftPressed()) {
            setDirection("left");
        } else if (keyH.isRightPressed()) {
            setDirection("right");
        }

        setCollisionOn(false);
        gamePanel.getCollisionChecker().checkTile(this);
        int objectIndex = gamePanel.getCollisionChecker().checkObject(this, true);
        pickUpObject(objectIndex);

        if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {
            isItStaying = false;
            if (isCollisionOn() == false) {
                switch (getDirection()) {
                    case "up":
                        moveUp();
                        break;
                    case "down":
                        moveDown();
                        break;
                    case "left":
                        moveLeft();
                        break;
                    case "right":
                        moveRight();
                        break;
                }
            }
        } else {
            isItStaying = true;
        }

        increaseSpriteCounter();
        if (getSpriteCounter() > 8) {
            nextAnimation(6);
            setSpriteCounter(0);
        }
    }
    
    public void pickUpObject(int objectIndex) {
        if (objectIndex != -1) {
            String objectName = gamePanel.getObjects()[objectIndex].getName();
            boolean isInteractPressed = keyH.isInteractPressed();

            switch (objectName) {
                case "key":
                    hasKey++;
                    gamePanel.getObjects()[objectIndex] = null;
                    gamePanel.getUIVariable().showMessage("You picked up a key");
                    break;
            
                case "chest":
                    boolean isOpen = ((Chest) gamePanel.getObjects()[objectIndex]).isOpen();
                    if (isInteractPressed) {
                        if (!isOpen && hasKey > 0) {
                            hasKey--;
                            ((Chest) gamePanel.getObjects()[objectIndex]).openChest();
                            gamePanel.getUIVariable().showMessage("You opened the chest");
                        } else if (isOpen) { 
                            gamePanel.getUIVariable().showMessage("The chest is already opened");
                        } else if (hasKey == 0) {
                            gamePanel.getUIVariable().showMessage("You need a key to open the chest");
                        }
                    }
                    break;
            }
            
            
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
        //g2.drawRect(screenX + getSolidArea().x, screenY + getSolidArea().y, getSolidArea().width, getSolidArea().height);
    }
    
    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getNumberOfKeys() {
        return hasKey;
    }
}
