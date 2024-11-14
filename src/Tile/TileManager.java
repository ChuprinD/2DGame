package Tile;

import Engine.GamePanel;
import Utils.ImageUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        // Load Water Tiles
        Set<String> emptyWaterTiles = new HashSet<>();
        Set<String> solidWaterTiles = new HashSet<>();
        int spriteSheetWaterRows = 6;
        int spriteSheetWaterColumns = 3;
        String spriteSheetWaterPath = "Water_Tile";

        for (int row = 0; row < spriteSheetWaterRows; row++) {
            for (int col = 0; col < spriteSheetWaterColumns; col++) {
                solidWaterTiles.add(row + ":" + col);

                if ((row == 3 || row == 4) && col > 1)
                    emptyWaterTiles.add(row + ":" + col);
            }
        }

        loadSpriteSheet(spriteSheetWaterPath, spriteSheetWaterRows, spriteSheetWaterColumns, originalTileSize,
                emptyWaterTiles, solidWaterTiles);

        // Load Path Tiles
        Set<String> emptyPathTiles = new HashSet<>();
        Set<String> solidPathTiles = new HashSet<>();
        int spriteSheetPathRows = 6;
        int spriteSheetPathColumns = 3;
        String spriteSheetPathPath = "Path_Tile";

        for (int row = 0; row < spriteSheetPathRows; row++) {
            for (int col = 0; col < spriteSheetPathColumns; col++) {
                if ((row == 3 || row == 4) && col > 1)
                    emptyPathTiles.add(row + ":" + col);
            }
        }

        loadSpriteSheet(spriteSheetPathPath, spriteSheetPathRows, spriteSheetPathColumns, originalTileSize,
                emptyPathTiles, solidPathTiles);

        // Load Cliff Tiles
        Set<String> emptyCliffTiles = new HashSet<>();
        Set<String> solidCliffTiles = new HashSet<>();
        int spriteSheetCliffRows = 6;
        int spriteSheetCliffColumns = 3;
        String spriteSheetCliffPath = "Cliff_Tile";

        for (int row = 0; row < spriteSheetPathRows; row++) {
            for (int col = 0; col < spriteSheetPathColumns; col++) {
                if ((row == 3 || row == 4) && col > 1)
                    emptyCliffTiles.add(row + ":" + col);

                if ((row != 1 || col != 1) && row != 5) {
                    solidCliffTiles.add(row + ":" + col);
                }
            }
        }

        loadSpriteSheet(spriteSheetCliffPath, spriteSheetCliffRows, spriteSheetCliffColumns, originalTileSize,
                emptyCliffTiles, solidCliffTiles);

        // Load Beach Tiles
        Set<String> emptyBeachTiles = new HashSet<>();
        Set<String> solidBeachTiles = new HashSet<>();
        int spriteSheetBeachRows = 3;
        int spriteSheetBeachColumns = 5;
        String spriteSheetBeachPath = "Beach_Tile";

        for (int row = 0; row < spriteSheetBeachRows; row++) {
            for (int col = 0; col < spriteSheetBeachColumns; col++) {
                if (row == 2 && col > 2)
                    emptyBeachTiles.add(row + ":" + col);

                if (row != 1 || col != 1)
                    solidBeachTiles.add(row + ":" + col);
            }
        }

        loadSpriteSheet(spriteSheetBeachPath, spriteSheetBeachRows, spriteSheetBeachColumns, originalTileSize,
                emptyBeachTiles, solidBeachTiles);
    }
    
    public void loadSpriteSheet(String spriteSheetPath, int rows, int columns, int originalTileSize, Set<String> emptyTiles, Set<String> solidTiles) {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/Tiles/" + spriteSheetPath + ".png"));

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if (emptyTiles.contains(row + ":" + col))
                        continue;
                    BufferedImage tileImage = spriteSheet.getSubimage(originalTileSize * col, originalTileSize * row,
                            originalTileSize, originalTileSize);
                    tileImage = ImageUtils.scaleImage(tileImage, gamePanel.getTileSize(), gamePanel.getTileSize());
                    
                    if (solidTiles.contains(row + ":" + col)) {
                        tiles.put(tiles.size(), new Tile(tileImage, true));
                    } else {
                        tiles.put(tiles.size(), new Tile(tileImage, false));
                    }
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

            if (worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() &&
                worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX() &&
                worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY() &&
                worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()) {

                g2.drawImage(tiles.get(tileNum).getImage(), screenX, screenY, null);
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
