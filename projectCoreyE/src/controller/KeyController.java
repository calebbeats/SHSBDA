package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.Shooter;

public class KeyController implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                //act the same as hitting left key
            case KeyEvent.VK_LEFT:
                shooter.translate(-5, 0);
                break;
            case KeyEvent.VK_D:
                //act the same as right key
            case KeyEvent.VK_RIGHT:
                shooter.translate(5, 0);
                break;
            case KeyEvent.VK_W:
                //act the same as up key
            case KeyEvent.VK_UP:
                shooter.translate(0, -5);
                break;
           case KeyEvent.VK_S:
                //act the same as down key
            case KeyEvent.VK_DOWN:
                shooter.translate(0, 5);
                break;
            case KeyEvent.VK_ALT:
                Main.gameData.enemyCount = 15;
                Main.gameData.score = 1500;
                Main.gameData.enemyKilled = 15;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
