package Assets.Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Engine.GamePanel;

public class SuperObject {
    private BufferedImage image;
    private String name;
    private boolean collision = false;
    private int worldX, worldY;
    private Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    private int defaultSolidAreaX = 0, defaultSolidAreaY = 0;

    SuperObject(String name) {
        this.name = name;
    }

    public void draw(Graphics2D g2, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

        if (worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() &&
                worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX()
                        + gamePanel.getPlayer().getScreenX()
                &&
                worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY()
                        - gamePanel.getPlayer().getScreenY()
                &&
                worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY()
                        + gamePanel.getPlayer().getScreenY()) {

            g2.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            //g2.drawRect(screenX + getSolidArea().x, screenY + getSolidArea().y, getSolidArea().width, getSolidArea().height);
        }
    }
    
    public void setSolidArea(int x, int y, int width, int height) {
        solidArea.x = x;
        solidArea.y = y;
        solidArea.width = width;
        solidArea.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
    
    public boolean isCollision() {
        return collision;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }
    
    public int getDefaultSolidAreaX() {
        return defaultSolidAreaX;
    }
    
    public int getDefaultSolidAreaY() {
        return defaultSolidAreaY;
    }

    public void update() {
        
    }
}
