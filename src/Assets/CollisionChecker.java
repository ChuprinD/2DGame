package Assets;

import Assets.Entity.Entity;
import Engine.GamePanel;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX / gamePanel.getTileSize();
        int entityRightCol = entityRightWorldX / gamePanel.getTileSize();
        int entityTopRow = entityTopWorldY / gamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY / gamePanel.getTileSize();

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                if (gamePanel.getTileManager().getTiles().get(tileNum1).isCollision()
                        || gamePanel.getTileManager().getTiles().get(tileNum2).isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                if (gamePanel.getTileManager().getTiles().get(tileNum1).isCollision()
                        || gamePanel.getTileManager().getTiles().get(tileNum2).isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                if (gamePanel.getTileManager().getTiles().get(tileNum1).isCollision()
                        || gamePanel.getTileManager().getTiles().get(tileNum2).isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                if (gamePanel.getTileManager().getTiles().get(tileNum1).isCollision()
                        || gamePanel.getTileManager().getTiles().get(tileNum2).isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
        }
    }
    
    public int checkObject(Entity entity, boolean player) {
        int index = -1;
        for (int i = 0; i < gamePanel.getObjects().length; i++) {
            if (gamePanel.getObjects()[i] != null) {
                entity.getSolidArea().x += entity.getWorldX();
                entity.getSolidArea().y += entity.getWorldY();

                gamePanel.getObjects()[i].getSolidArea().x = gamePanel.getObjects()[i].getWorldX();
                gamePanel.getObjects()[i].getSolidArea().y = gamePanel.getObjects()[i].getWorldY();

                switch (entity.getDirection()) {
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(gamePanel.getObjects()[i].getSolidArea())) {
                            if (gamePanel.getObjects()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        if (entity.getSolidArea().intersects(gamePanel.getObjects()[i].getSolidArea())) {
                            if (gamePanel.getObjects()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(gamePanel.getObjects()[i].getSolidArea())) {
                            if (gamePanel.getObjects()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        if (entity.getSolidArea().intersects(gamePanel.getObjects()[i].getSolidArea())) {
                            if (gamePanel.getObjects()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.getSolidArea().x = entity.getDefaultSolidAreaX();
                entity.getSolidArea().y = entity.getDefaultSolidAreaY();
                gamePanel.getObjects()[i].getSolidArea().x = entity.getDefaultSolidAreaX();
                gamePanel.getObjects()[i].getSolidArea().y = entity.getDefaultSolidAreaY();
            }
        }

        return index;
    }
}