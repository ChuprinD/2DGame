package MapEditor;

import Tile.Tile;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Stack;

import javax.swing.SwingUtilities;

public class MapController {
    private int maxWorldRow, maxWorldCol;
    private int currentTileSize;
    private Integer[][] mapData;
    private Map<Integer, Tile> tiles;
    private Integer selectedSpriteId = -1;
    private Point selectionStart, selectionEnd;
    private int offsetX = 0, offsetY = 0;
    private MapPanel mapPanel;
    private Stack<Integer[][]> undoStack = new Stack<>();

    public MapController(int maxWorldRow, int maxWorldCol, int currentTileSize, Map<Integer, Tile> tiles) {
        this.maxWorldRow = maxWorldRow;
        this.maxWorldCol = maxWorldCol;
        this.currentTileSize = currentTileSize;
        this.tiles = tiles;
        initializeMap();
    }

    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    public Map<Integer, Tile> getTiles() {
        return tiles;
    }

    public void initializeMap() {
        mapData = new Integer[maxWorldRow][maxWorldCol];
        for (int y = 0; y < maxWorldRow; y++) {
            for (int x = 0; x < maxWorldCol; x++) {
                mapData[y][x] = -1;
            }
        }
    }

    public Integer[][] getMapData() {
        return mapData;
    }

    public void setMapData(Integer[][] mapData) {
        this.mapData = mapData;
    }

    public void saveState() {
        if (undoStack.size() == 5) { 
            undoStack.remove(0); 
        }

        Integer[][] mapDataCopy = new Integer[maxWorldRow][maxWorldCol];
        for (int y = 0; y < maxWorldRow; y++) {
            System.arraycopy(mapData[y], 0, mapDataCopy[y], 0, maxWorldCol);
        }
        undoStack.push(mapDataCopy);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            mapData = undoStack.pop();
            mapPanel.repaint(); 
        }
    }

    public void resizeMap(int newRows, int newCols) {
        maxWorldRow = newRows;
        maxWorldCol = newCols;
        initializeMap();
    }

    public void renderMap(Graphics g, int panelWidth, int panelHeight) {
        currentTileSize = Math.min(panelWidth / maxWorldCol, panelHeight / maxWorldRow);
        offsetX = (panelWidth - (maxWorldCol * currentTileSize)) / 2;
        offsetY = (panelHeight - (maxWorldRow * currentTileSize)) / 2;

        for (int y = 0; y < maxWorldRow; y++) {
            for (int x = 0; x < maxWorldCol; x++) {
                int tileId = mapData[y][x];
                int drawX = x * currentTileSize + offsetX;
                int drawY = y * currentTileSize + offsetY;

                if (tileId != -1 && tiles.containsKey(tileId)) {
                    BufferedImage tileImage = tiles.get(tileId).getImage();
                    g.drawImage(tileImage, drawX, drawY, currentTileSize, currentTileSize, null);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(drawX, drawY, currentTileSize, currentTileSize);
                }

                g.setColor(Color.DARK_GRAY);
                g.drawRect(drawX, drawY, currentTileSize, currentTileSize);
            }
        }

        if (selectionStart != null && selectionEnd != null) {
            int startX = Math.min(selectionStart.x, selectionEnd.x);
            int endX = Math.max(selectionStart.x, selectionEnd.x);
            int startY = Math.min(selectionStart.y, selectionEnd.y);
            int endY = Math.max(selectionStart.y, selectionEnd.y);

            g.setColor(new Color(0, 255, 0, 100));
            for (int y = startY; y <= endY; y++) {
                for (int x = startX; x <= endX; x++) {
                    int drawX = x * currentTileSize + offsetX;
                    int drawY = y * currentTileSize + offsetY;
                    g.fillRect(drawX, drawY, currentTileSize, currentTileSize);
                }
            }
        }
    }

    public MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = (e.getX() - offsetX) / currentTileSize;
                int y = (e.getY() - offsetY) / currentTileSize;
        
                if (x >= 0 && x < maxWorldCol && y >= 0 && y < maxWorldRow) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        selectionStart = new Point(x, y);
                        selectionEnd = selectionStart;
                        mapPanel.repaint();
                    } else if (SwingUtilities.isLeftMouseButton(e)) {
                        selectionStart = new Point(x, y);
                        selectionEnd = selectionStart;
                        mapPanel.repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectionStart != null && selectionEnd != null) {
                    saveState();
                    int startX = Math.min(selectionStart.x, selectionEnd.x);
                    int endX = Math.max(selectionStart.x, selectionEnd.x);
                    int startY = Math.min(selectionStart.y, selectionEnd.y);
                    int endY = Math.max(selectionStart.y, selectionEnd.y);
        
                    if (SwingUtilities.isLeftMouseButton(e) && selectedSpriteId != -1) {
                        for (int y = startY; y <= endY; y++) {
                            for (int x = startX; x <= endX; x++) {
                                mapData[y][x] = selectedSpriteId;
                            }
                        }
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        for (int y = startY; y <= endY; y++) {
                            for (int x = startX; x <= endX; x++) {
                                mapData[y][x] = -1;
                            }
                        }
                    }
                    
                    selectionStart = null;
                    selectionEnd = null;
                    mapPanel.repaint();
                }
            }
        };
    }

    public MouseMotionAdapter getMouseMotionAdapter() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = (e.getX() - offsetX) / currentTileSize;
                int y = (e.getY() - offsetY) / currentTileSize;
        
                if (x >= 0 && x < maxWorldCol && y >= 0 && y < maxWorldRow) {
                    selectionEnd = new Point(x, y);
                    mapPanel.repaint();
                }
            }
        };
    }

    public void setSelectedSpriteId(Integer id) {
        this.selectedSpriteId = id;
    }
}
