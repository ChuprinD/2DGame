package Main;

import javax.swing.JFrame;

import Engine.GamePanel;
import MapEditor.MapEditor;

public class Main {
    public static void main(String[] args) throws Exception {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

        //MapEditor mapEditor = new MapEditor();
        //mapEditor.run();
    }
}
