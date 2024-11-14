package MapEditor;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import Tile.Tile;

public class SpritePanel extends JPanel {
    public SpritePanel(MapController mapController) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        Map<Integer, Tile> tiles = mapController.getTiles();

        for (int id : tiles.keySet()) {
            ImageIcon icon = new ImageIcon(tiles.get(id).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            JButton spriteButton = new JButton(icon);
            spriteButton.setPreferredSize(new Dimension(45, 32));

            spriteButton.addActionListener(e -> {
                mapController.setSelectedSpriteId(id);
                resetButtonColors();
                spriteButton.setBackground(Color.RED);
            });
            add(spriteButton);
        }
    }

    private void resetButtonColors() {
        for (Component component : getComponents()) {
            if (component instanceof JButton) {
                component.setBackground(null);
            }
        }
    }
}
