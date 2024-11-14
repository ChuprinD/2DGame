package Assets.Entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    private int worldX, worldY;
    private int width, height;
    private int speed;
    private String direction;

    private BufferedImage moveSpriteSheet;
    private int currentAnimation = 0;
    private int spriteCounter = 0;

    private Rectangle solidArea;
    private int defaultSolidAreaX, defaultSolidAreaY;
    private boolean collisionOn = false;

    public Entity(int x, int y, int width, int height, int speed) {
        this.worldX = x;
        this.worldY = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.direction = "down";
    }
    
    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    public int getdefaultSolidAreaX() {
        return defaultSolidAreaX;
    }

    public int getdefaultSolidAreaY() {
        return defaultSolidAreaY;
    }
    
    public void setdefaultSolidArea(int x, int y) {
        defaultSolidAreaX = x;
        defaultSolidAreaY = y;
    }

    public void setMoveSpriteSheet(BufferedImage moveSpriteSheet) {
        this.moveSpriteSheet = moveSpriteSheet;
    }

    public BufferedImage getMoveSpriteSheet() {
        return moveSpriteSheet;
    }

    public void increaseSpriteCounter() {
        spriteCounter++;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getCurrentAnimation() {
        return currentAnimation;
    }

    public void nextAnimation(int maxAnimation) {
        currentAnimation = (currentAnimation + 1) % maxAnimation;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void moveUp() {
        worldY -= speed;
    }

    public void moveDown() {
        worldY += speed;
    }

    public void moveLeft() {
        worldX -= speed;
    }

    public void moveRight() {   
        worldX += speed;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public int getDefaultSolidAreaX() {
        return defaultSolidAreaX;
    }

    public int getDefaultSolidAreaY() {
        return defaultSolidAreaY;
    }
}
