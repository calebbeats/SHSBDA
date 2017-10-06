package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.Melee;
import model.Missile;
import model.Shooter;

public class MouseController extends MouseAdapter {

    private int px;
    private int py;

    Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);

    @Override
    public void mousePressed(MouseEvent me) {
        px = me.getX();
        py = me.getY();

<<<<<<< HEAD
        if (!Main.isPaused) {//if game is paused don't do anything after unpausing
            if (me.getButton() == MouseEvent.BUTTON1 && !shooter.isSprint()) {//Left click detected, initiate melee attack
                //melee attack in the direction the mouse is in regards to the player
                Melee m = new Melee(
                        shooter.getXofMissileShoot(),
                        shooter.getYofMissileShoot(),
                        px, py // direction in which to melee attack
                );
                Main.gameData.friendFigures.add(m);
            }

            if (me.getButton() == MouseEvent.BUTTON3 && !shooter.isSprint()) { //Right click detected, initiate ranged attack
                //shoot a missle at the mouse press location
                Missile m = new Missile(
                        shooter.getXofMissileShoot(),
                        shooter.getYofMissileShoot(),
                        px, py // target location where the missile explodes
                );
                Main.gameData.friendFigures.add(m);
            }
=======
        Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);
        if (me.getButton() == MouseEvent.BUTTON1) {//Left click detected, initiate melee attack
            //melee attack in the direction the mouse is in regards to the player
            Melee m = new Melee(
                    shooter.getXofMissileShoot(),
                    shooter.getYofMissileShoot(),
                    px, py // direction in which to melee attack
            );
            Main.gameData.friendFigures.add(m);
        }

        if (me.getButton() == MouseEvent.BUTTON3) { try {
            //Right click detected, initiate ranged attack
            //shoot a missle at the mouse press location
            audio();
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(MouseController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MouseController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(MouseController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(MouseController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Missile m = new Missile(
                    shooter.getXofMissileShoot(),
                    shooter.getYofMissileShoot(),
                    px, py // target location where the missile explodes
            );
            Main.gameData.friendFigures.add(m);
>>>>>>> Sound-effects-for-killing-enemies-and-shooting-missles
        }
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

    @Override
    public void mouseMoved(MouseEvent e) {
        shooter.setMouseMovedEvent(e);        
    }
}
