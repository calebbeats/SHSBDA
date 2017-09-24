package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.Shooter;

public class KeyController implements KeyListener {

    Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                shooter.setVelocityY(-2);
                break;
            case KeyEvent.VK_A:
                shooter.setVelocityX(-2);
                break;
            case KeyEvent.VK_S:
                shooter.setVelocityY(2);
                break;
            case KeyEvent.VK_D:
                shooter.setVelocityX(2);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                shooter.setVelocityY(0);
                break;
            case KeyEvent.VK_A:
                shooter.setVelocityX(0);
                break;
            case KeyEvent.VK_S:
                shooter.setVelocityY(0);
                break;
            case KeyEvent.VK_D:
                shooter.setVelocityX(0);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
