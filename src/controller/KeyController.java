package controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.Consumable;
import model.Shooter;
import javax.swing.JOptionPane;
import model.MyBullet;

public class KeyController implements KeyListener {

    public static final int VELOCITY_X = 2, VELOCITY_Y = 2, VELOCITY_ZERO = 0;

    private final Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);
    private boolean[] keyDown = new boolean[4];

    public static boolean chooseMissile = false;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Set player's velocity when WASD keys are pressed
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
            case KeyEvent.VK_NUMPAD0: {
                chooseMissile = true;

                if (chooseMissile) {
                    try {
                        audio();
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(KeyController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(KeyController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(KeyController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(KeyController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                MyBullet bullet1 = new MyBullet(shooter.getXofMissileShoot(), shooter.getYofMissileShoot(),
                        shooter.getXofMissileShoot(), 0,
                        Color.GREEN);
                MyBullet bullet2 = new MyBullet(shooter.getXofMissileShoot() + 10, shooter.getYofMissileShoot() + 15,
                        shooter.getXofMissileShoot() + 15, 15,
                        Color.RED);

                synchronized (Main.gameData.friendFigures) {
                    Main.gameData.friendFigures.add(bullet1);
                }
                synchronized (Main.gameData.friendFigures) {
                    Main.gameData.friendFigures.add(bullet2);
                }
            }

            break;

            case KeyEvent.VK_SHIFT:
                shooter.isSprint(true);
                break;
            case KeyEvent.VK_1:
                shooter.useItem((Consumable) shooter.inventory[0], 0);
                break;
            case KeyEvent.VK_2:
                shooter.useItem((Consumable) shooter.inventory[1], 1);
                break;
            case KeyEvent.VK_3:
                shooter.useItem((Consumable) shooter.inventory[2], 2);
                break;
            case KeyEvent.VK_4:
                //shooter.useItem((Consumable) shooter.inventory[3], 3);
                shooter.testItem();
                break;
            case KeyEvent.VK_ESCAPE: //pause the game when escape key is pressed
                if (Main.isPaused) {
                    Main.isPaused = false;
                } else {
                    Main.isPaused = true;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Set player's velocity to 0 when WASD keys are released
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
            case KeyEvent.VK_SHIFT:
                shooter.isSprint(false);
                break;
        }

        // Allow movement to continue if multiple keys are pressed simultaneously
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

    private int showConfirmDialog() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void audio() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        AudioInputStream stream = null;
        try {
            //File file = new File("C:/Users/dinhn/Documents/GitHub/SHSBDA/PatakasWorld.wav");
            stream = AudioSystem.getAudioInputStream(getClass().getResource("climactic-boom.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            stream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
