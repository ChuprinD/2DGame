package MapEditor;

import javax.swing.*;
import Tile.Tile;
import Tile.TileManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class MapEditor extends JFrame {
    private final int originalTileSize = 16;

    private final int maxWorldCol = 50;
    private final int maxWorldRow = 50;

    private final JButton[][] gridButtons = new JButton[maxWorldRow][maxWorldCol];
    private BufferedImage selectedSprite;
    private Integer selectedSpriteId = -1;  
    private final Integer[][] mapData = new Integer[maxWorldRow][maxWorldCol];

    public void run() {

        Map<Integer, Tile> tiles = new TileManager(this.originalTileSize, this.maxWorldCol, this.maxWorldRow).getTiles();
        this.setTitle("Map Editor");
        this.setLayout(new BorderLayout());

        setUpGrid();

        setUpSpritePanel(tiles);

        JButton saveButton = new JButton("Save Map");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("Enter file name to save:");
                if (fileName != null && !fileName.trim().isEmpty()) {
                    saveMapToFile(fileName.trim());
                }
            }
        });

        JButton loadButton = new JButton("Load Map");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("Enter file name to load:");
                if (fileName != null && !fileName.trim().isEmpty()) {
                    loadMapFromFile(fileName.trim(), tiles);
                }
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        this.add(topPanel, BorderLayout.NORTH);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void saveMapToFile(String fileName) {
        try (FileWriter writer = new FileWriter("src/resources/maps/" + fileName + ".txt")) {
            for (int y = 0; y < maxWorldRow; y++) {
                StringBuilder line = new StringBuilder();
                for (int x = 0; x < maxWorldCol; x++) {
                    line.append(mapData[y][x]).append(" "); 
                }
                writer.write(line.toString().trim() + "\n"); 
            }
            JOptionPane.showMessageDialog(this, "Map saved successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving the map.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadMapFromFile(String fileName, Map<Integer, Tile> tiles) {
        try {
            Scanner scanner = new Scanner(new File("src/resources/maps/" + fileName + ".txt"));
            for (int y = 0; y < maxWorldRow; y++) {
                if (!scanner.hasNextLine())
                    break;
                String[] line = scanner.nextLine().trim().split(" ");
                for (int x = 0; x < maxWorldCol; x++) {
                    int tileId = Integer.parseInt(line[x]);
                    mapData[y][x] = tileId;

                    if (tileId != -1 && tiles.containsKey(tileId)) {
                        gridButtons[y][x].setIcon(new ImageIcon(tiles.get(tileId).getImage()
                                .getScaledInstance(originalTileSize, originalTileSize, Image.SCALE_SMOOTH)));
                    } else {
                        gridButtons[y][x].setIcon(null);
                    }
                }
            }
            scanner.close();
            JOptionPane.showMessageDialog(this, "Map loaded successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading the map.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setUpGrid() {
        for (int y = 0; y < maxWorldRow; y++) {
            for (int x = 0; x < maxWorldCol; x++) {
                mapData[y][x] = -1;
            }
        }

        JPanel gridPanel = new JPanel(new GridLayout(maxWorldRow, maxWorldCol));
        for (int y = 0; y < maxWorldRow; y++) {
            for (int x = 0; x < maxWorldCol; x++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(originalTileSize, originalTileSize));

                final int finalX = x;
                final int finalY = y;

                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e) && selectedSprite != null) {
                            button.setIcon(new ImageIcon(selectedSprite));
                            mapData[finalY][finalX] = selectedSpriteId;
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            button.setIcon(null);
                            mapData[finalY][finalX] = -1;
                        }
                    }
                });

                gridButtons[y][x] = button;
                gridPanel.add(button);
            }
        }
        this.add(gridPanel, BorderLayout.CENTER);
    }

    private void setUpSpritePanel(Map<Integer, Tile> tiles) {
        JPanel spritePanel = new JPanel();
        spritePanel.setLayout(new BoxLayout(spritePanel, BoxLayout.X_AXIS));
        for (int id = 0; id < tiles.size(); id++) {
            final int spriteId = id;
            System.out.println("\n" + id);
            ImageIcon icon = new ImageIcon(tiles.get(id).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            JButton spriteButton = new JButton(icon);
            spriteButton.setPreferredSize(new Dimension(32, 32));

            spriteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedSprite = tiles.get(spriteId).getImage();
                    selectedSpriteId = spriteId;
                }
            });
            spritePanel.add(spriteButton);
        }

        JScrollPane spriteScrollPane = new JScrollPane(spritePanel);
        spriteScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        spriteScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        spriteScrollPane.setPreferredSize(new Dimension(500, 60));

        this.add(spriteScrollPane, BorderLayout.SOUTH);
    }
    
    public int getOriginalTileSize() {
        return originalTileSize;
    }

    public int getMaxScreenCol() {
        return maxWorldCol;
    }

    public int getMaxScreenRow() {
        return maxWorldRow;
    }
}
