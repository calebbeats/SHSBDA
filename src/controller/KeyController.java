package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.Shooter;

public class KeyController implements KeyListener {

    public static final int VELOCITY_X = 2, VELOCITY_Y = 2, VELOCITY_ZERO = 0;

    private final Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);

    private boolean[] keyDown = new boolean[4];

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                shooter.setVelocityY(-VELOCITY_Y);
                keyDown[0] = true;
                break;
            case KeyEvent.VK_A:
                shooter.setVelocityX(-VELOCITY_X);
                keyDown[1] = true;
                break;
            case KeyEvent.VK_S:
                shooter.setVelocityY(VELOCITY_Y);
                keyDown[2] = true;
                break;
            case KeyEvent.VK_D:
                shooter.setVelocityX(VELOCITY_X);
                keyDown[3] = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keyDown[0] = false;
                break;
            case KeyEvent.VK_A:
                keyDown[1] = false;
                break;
            case KeyEvent.VK_S:
                keyDown[2] = false;
                break;
            case KeyEvent.VK_D:
                keyDown[3] = false;
                break;
        }

        if (keyDown[1] && !keyDown[3]) {
            shooter.setVelocityX(-VELOCITY_X);
        } else if (!keyDown[1] && keyDown[3]) {
            shooter.setVelocityX(VELOCITY_X);
        } else if (!keyDown[1] && !keyDown[3]) {
            shooter.setVelocityX(VELOCITY_ZERO);
        }

        if (keyDown[0] && !keyDown[2]) {
            shooter.setVelocityY(-VELOCITY_Y);
        } else if (!keyDown[0] && keyDown[2]) {
            shooter.setVelocityY(VELOCITY_Y);
        } else if (!keyDown[0] && !keyDown[2]) {
            shooter.setVelocityY(VELOCITY_ZERO);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
