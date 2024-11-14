package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_UP, KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_E:
                interactPressed = true;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_UP, KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_E:
                interactPressed = false;
            default:
                break;
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isInteractPressed() {
        return interactPressed;
    }
    
}
