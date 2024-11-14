package MapEditor;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private MapController mapController;

    public MapPanel(MapController mapController) {
        this.mapController = mapController;
        addMouseListener(mapController.getMouseAdapter());
        addMouseMotionListener(mapController.getMouseMotionAdapter());
    }

    public void setMapData(Integer[][] mapData) {
        mapController.setMapData(mapData);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        mapController.renderMap(g, getWidth(), getHeight());
    }
}
