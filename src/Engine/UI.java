package Engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Assets.Object.Key;

public class UI {

    private GamePanel gamePanel;
    private Font arial_40;
    private BufferedImage keyImage;
    private boolean messageOn = false;
    private String message = "";
    private int messageCounter = 0;
    

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        Key key = new Key();
        this.keyImage = key.getImage();
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics g2) {

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, gamePanel.getTileSize() / 2, gamePanel.getTileSize() / 2, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2.drawString("x " + gamePanel.getPlayer().getNumberOfKeys(), 90, 80);

        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(30F));
            FontMetrics metrics = g2.getFontMetrics(g2.getFont());
            int messageWidth = metrics.stringWidth(message);
            int messageHeight = metrics.getHeight();

            int messageX = (gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getWidth() / 2) - messageWidth / 2;
            int messageY = gamePanel.getPlayer().getScreenY() - messageHeight / 2;

            g2.drawString(message, messageX, messageY);

            messageCounter++;

            if (messageCounter > 60) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
    
}
