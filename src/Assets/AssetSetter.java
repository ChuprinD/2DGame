package Assets;

import Assets.Object.Chest;
import Assets.Object.Key;
import Engine.GamePanel;

public class AssetSetter {
    private GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    public void setObjects() {
        gamePanel.getObjects()[0] = new Key();
        gamePanel.getObjects()[0].setWorldX(28 * gamePanel.getTileSize());
        gamePanel.getObjects()[0].setWorldY(24 * gamePanel.getTileSize());
        gamePanel.getObjects()[0].setSolidArea(0, 0, gamePanel.getTileSize(), gamePanel.getTileSize());
        
        gamePanel.getObjects()[1] = new Chest();
        gamePanel.getObjects()[1].setWorldX(28 * gamePanel.getTileSize());
        gamePanel.getObjects()[1].setWorldY(30 * gamePanel.getTileSize());
        gamePanel.getObjects()[1].setSolidArea(0, 0, gamePanel.getTileSize(), gamePanel.getTileSize());
    }
}
