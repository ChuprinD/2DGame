package Entity;

import java.awt.image.BufferedImage;

public class Entity {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private String direction;

    private BufferedImage moveSpriteSheet;
    private int currentAnmation = 0;
    private int spriteCounter = 0;

    public Entity(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.direction = "down";
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
        return currentAnmation;
    }

    public void nextAnimation(int maxAnimation) {
        currentAnmation = (currentAnmation + 1) % maxAnimation;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {   
        x += speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
}
