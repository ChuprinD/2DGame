package MapEditor;

import javax.swing.*;
import Engine.GamePanel;
import Tile.TileManager;
import java.awt.*;

public class MapEditor extends JFrame {
    private GamePanel gamePanel;
    private MapPanel mapPanel;
    private SpritePanel spritePanel;
    private MapController mapController;
    private FileManager fileManager;
    private JTextField rowsField;
    private JTextField colsField;
    private int maxWorldCol = 50;
    private int maxWorldRow = 50;
    private int currentTileSize;

    public void run() {
        setTitle("Map Editor");
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        initializeComponents();

        mapController.initializeMap();
        mapPanel.setMapData(mapController.getMapData());
        fileManager.setMapPanel(mapPanel);
        setUpTopPanel();

        add(mapPanel, BorderLayout.CENTER);

        JScrollPane spriteScrollPane = new JScrollPane(spritePanel);
        spriteScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        spriteScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        spriteScrollPane.setPreferredSize(new Dimension(500, 80)); 
        add(spriteScrollPane, BorderLayout.SOUTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 3;
        int height = screenSize.height * 2 / 3;

        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        gamePanel = new GamePanel();
        currentTileSize = gamePanel.getOriginalTileSize();
        mapController = new MapController(maxWorldRow, maxWorldCol, currentTileSize, new TileManager(gamePanel).getTiles());
        mapPanel = new MapPanel(mapController);
        mapController.setMapPanel(mapPanel);
        spritePanel = new SpritePanel(mapController);
        fileManager = new FileManager(mapController, rowsField, colsField);
    }

    private void setUpTopPanel() {
        JPanel topPanel = new JPanel();
        rowsField = new JTextField(String.valueOf(maxWorldRow), 3);
        colsField = new JTextField(String.valueOf(maxWorldCol), 3);

        fileManager.setColsField(colsField);
        fileManager.setRowsField(rowsField);

        JButton resizeButton = new JButton("Resize Map");
        resizeButton.addActionListener(e -> resizeMap());

        JButton saveButton = new JButton("Save Map");
        saveButton.addActionListener(e -> fileManager.saveMap());

        JButton loadButton = new JButton("Load Map");
        loadButton.addActionListener(e -> fileManager.loadMap());

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            mapController.undo(); 
            mapPanel.repaint();  
        });

        topPanel.add(new JLabel("Rows:"));
        topPanel.add(rowsField);
        topPanel.add(new JLabel("Columns:"));
        topPanel.add(colsField);
        topPanel.add(resizeButton);
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(undoButton);

        add(topPanel, BorderLayout.NORTH);
    }

    private void resizeMap() {
        try {
            maxWorldRow = Integer.parseInt(rowsField.getText());
            maxWorldCol = Integer.parseInt(colsField.getText());
            mapController.resizeMap(maxWorldRow, maxWorldCol);
            mapPanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid integers for rows and columns.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
