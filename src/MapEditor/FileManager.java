package MapEditor;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
    private MapController mapController;
    private JTextField rowsField, colsField;
    private MapPanel mapPanel;

    public FileManager(MapController mapController, JTextField rowsField, JTextField colsField) {
        this.mapController = mapController;
        this.rowsField = rowsField;
        this.colsField = colsField;
    }

    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    public void setRowsField(JTextField rowsField) {
        this.rowsField = rowsField;
    }

    public void setColsField(JTextField colsField) {
        this.colsField = colsField;
    }

    public void saveMap() {
        String fileName = JOptionPane.showInputDialog("Enter file name to save:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            try (FileWriter writer = new FileWriter("src/resources/maps/" + fileName + ".txt")) {
                Integer[][] mapData = mapController.getMapData();
                for (Integer[] row : mapData) {
                    for (int tile : row) {
                        writer.write(tile + " ");
                    }
                    writer.write("\n");
                }
                JOptionPane.showMessageDialog(null, "Map saved successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving the map.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void loadMap() {
        String fileName = JOptionPane.showInputDialog("Enter file name to load:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            try {
                File file = new File("src/resources/maps/" + fileName + ".txt");
                Scanner scanner = new Scanner(file);

                int rows = 0;
                int cols = 0;
                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().trim().split(" ");
                    rows++;
                    cols = Math.max(cols, line.length);
                }
                scanner.close();

                rowsField.setText(String.valueOf(rows));
                colsField.setText(String.valueOf(cols));
                mapController.resizeMap(rows, cols);

                scanner = new Scanner(file);
                Integer[][] mapData = mapController.getMapData();
                for (int y = 0; y < rows; y++) {
                    String[] line = scanner.nextLine().trim().split(" ");
                    for (int x = 0; x < cols; x++) {
                        mapData[y][x] = Integer.parseInt(line[x]);
                    }
                }
                scanner.close();
                mapPanel.repaint();
                JOptionPane.showMessageDialog(null, "Map loaded successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading the map.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
    