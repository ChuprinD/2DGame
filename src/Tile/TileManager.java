package Tile;

import Engine.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class TileManager {
    
    private GamePanel gamePanel;
    Map<Integer, Tile> tiles;
    int mapTileNum[][];
 
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        this.tiles = new HashMap<>();
        this.mapTileNum = new int[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];
        loadTilesImages(gamePanel.getOriginalTileSize());
        loadMap("world2");
    }

    public TileManager(int originalTileSize, int maxWorldCol, int maxWorldRow) {
        this.tiles = new HashMap<>();
        this.mapTileNum = new int[maxWorldCol][maxWorldRow];
        loadTilesImages(originalTileSize);
    }

    public Map<Integer, Tile> getTiles() {
        return tiles;
    }

    public void loadTilesImages(int originalTileSize) {
        try {
            BufferedImage imageWater = ImageIO
                    .read(getClass().getResourceAsStream("/resources/Tiles/Water_Tile.png"));

            int tileId = 0;
            int rows = 6;  
            int columns = 3; 

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if ((row == 3 || row == 4) && col > 1)
                        continue;

                    BufferedImage tileImage = imageWater.getSubimage(originalTileSize * col, originalTileSize * row,
                            originalTileSize, originalTileSize);

                    tiles.put(tileId, new Tile(tileImage, true));
                    
                    // if ((row == 1 && col == 1) || row == 5) {
                    //     tiles.put(tileId, new Tile(tileImage, true));
                    // } else {
                    //     tiles.put(tileId, new Tile(tileImage, false));
                    // }
                    
                    tileId++;
                }
            }

            BufferedImage imagePath = ImageIO.read(getClass().getResourceAsStream("/resources/Tiles/Path_Tile.png"));

            rows = 6;  
            columns = 3; 

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if ((row == 3 || row == 4) && col > 1)
                        continue;

                    BufferedImage tileImage = imagePath.getSubimage(originalTileSize * col, originalTileSize * row,
                            originalTileSize, originalTileSize);
                    tiles.put(tileId, new Tile(tileImage, false));
                    tileId++;
                }
            }

            BufferedImage imageCliff = ImageIO.read(getClass().getResourceAsStream("/resources/Tiles/Cliff_Tile.png"));
            
            rows = 6;  
            columns = 3; 

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if ((row == 3 || row == 4) && col > 1)
                        continue;

                    BufferedImage tileImage = imageCliff.getSubimage(originalTileSize * col, originalTileSize * row,
                            originalTileSize, originalTileSize);
                    if ((row == 1 && col == 1) || row == 5) {
                        tiles.put(tileId, new Tile(tileImage, false));
                    } else {
                        tiles.put(tileId, new Tile(tileImage, true));
                    }
                    
                    tileId++;
                }
            }
            
            BufferedImage imageBeach = ImageIO.read(getClass().getResourceAsStream("/resources/Tiles/Beach_Tile.png"));
            
            rows = 3;  
            columns = 5; 

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if (row == 2 && col > 2)
                        continue;

                    BufferedImage tileImage = imageBeach.getSubimage(originalTileSize * col, originalTileSize * row, originalTileSize, originalTileSize);
                    tiles.put(tileId, new Tile(tileImage, false));
                    tileId++;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadMap(String nameOfMap) {
        try {
            InputStream stream = getClass().getResourceAsStream("/resources/maps/" + nameOfMap + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            int currentCol = 0;
            int currentRow = 0;

            while (currentCol < gamePanel.getMaxWorldCol() && currentRow < gamePanel.getMaxWorldRow()) {
                String line = reader.readLine();

                while (currentCol < gamePanel.getMaxWorldCol()) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[currentCol]);

                    mapTileNum[currentCol][currentRow] = num;
                    currentCol++;
                }

                if (currentCol == gamePanel.getMaxWorldCol()) {
                    currentCol = 0;
                    currentRow++;
                }
            }

            reader.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2) {
        int currentCol = 0;
        int currentRow = 0;

        while (currentCol < gamePanel.getMaxWorldCol() && currentRow < gamePanel.getMaxWorldRow()) {
            int tileNum = mapTileNum[currentCol][currentRow];

            int worldX = currentCol * gamePanel.getTileSize();
            int worldY = currentRow * gamePanel.getTileSize();
            int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
            int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

            if (worldX
                    + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX()
                    &&
                    worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX()
                            + gamePanel.getPlayer().getScreenX()
                    &&
                    worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY()
                            - gamePanel.getPlayer().getScreenY()
                    &&
                    worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY()
                            + gamePanel.getPlayer().getScreenY()) {

                g2.drawImage(tiles.get(tileNum).getImage(), screenX, screenY, gamePanel.getTileSize(),
                        gamePanel.getTileSize(), null);
            }

            currentCol++;

            if (currentCol == gamePanel.getMaxWorldCol()) {
                currentCol = 0;
                currentRow++;
            }
        }
    }
    
    public int[][] getMapTileNum() {
        return mapTileNum;
    }
}
