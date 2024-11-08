package MapEditor;

import javax.swing.*;
import Tile.Tile;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class MapEditor extends JFrame {
    private final int originalTileSize = 16;

    private final int maxScreenCol = 50;
    private final int maxScreenRow = 50;

    private final JButton[][] gridButtons = new JButton[maxScreenRow][maxScreenCol];
    private BufferedImage selectedSprite;
    private Integer selectedSpriteId = -1;  
    private final Integer[][] mapData = new Integer[maxScreenRow][maxScreenCol];

    public void run(Map<Integer, Tile> tiles) {
        setTitle("Map Editor");
        setLayout(new BorderLayout());

        for (int y = 0; y < maxScreenRow; y++) {
            for (int x = 0; x < maxScreenCol; x++) {
                mapData[y][x] = -1;
            }
        }

        JPanel gridPanel = new JPanel(new GridLayout(maxScreenRow, maxScreenCol));
        for (int y = 0; y < maxScreenRow; y++) {
            for (int x = 0; x < maxScreenCol; x++) {
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
        add(gridPanel, BorderLayout.CENTER);

        JPanel spritePanel = new JPanel();
        spritePanel.setLayout(new BoxLayout(spritePanel, BoxLayout.X_AXIS));
        for (int id = 0; id < tiles.size(); id++) {
            final int spriteId = id;
            ImageIcon icon = new ImageIcon(tiles.get(id).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            JButton spriteButton = new JButton(icon);
            spriteButton.setPreferredSize(new Dimension(32, 32));

            spriteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedSprite = tiles.get(spriteId).getImage();
                    selectedSpriteId = spriteId; // Store the tile ID for saving
                }
            });
            spritePanel.add(spriteButton);
        }

        JScrollPane spriteScrollPane = new JScrollPane(spritePanel);
        spriteScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        spriteScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        spriteScrollPane.setPreferredSize(new Dimension(500, 60));

        add(spriteScrollPane, BorderLayout.SOUTH);

        JButton saveButton = new JButton("Save Map");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMapToFile();
            }
        });

        add(saveButton, BorderLayout.NORTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    private void saveMapToFile() {
        try (FileWriter writer = new FileWriter("map.txt")) {
            for (int y = 0; y < maxScreenRow; y++) {
                StringBuilder line = new StringBuilder();
                for (int x = 0; x < maxScreenCol; x++) {
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
}
