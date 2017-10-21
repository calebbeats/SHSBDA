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

        if (!Main.isPaused) {//if game is paused don't do anything after unpausing
            if (me.getButton() == MouseEvent.BUTTON1 && !shooter.isSprint()) {//Left click detected, initiate melee attack
                
                //Audio For Sword Swing
                //------------------------------
                try {
                    swordAudio();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                    Logger.getLogger(MouseController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //melee attack in the direction the mouse is in regards to the player
                Melee m = shooter.shootMelee(px,py, Shooter.getMeleeWeapon());
                Main.gameData.friendFigures.add(m);
            }

            if (me.getButton() == MouseEvent.BUTTON3 && !shooter.isSprint()) {

                if (KeyController.chooseMissile == false) {
                    try {
                        //Right click detected, initiate ranged attack
                        //shoot a missle at the mouse press location
                        fireballAudio();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                        Logger.getLogger(MouseController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Missile m = new Missile(
                            shooter.getXofMissileShoot(),
                            shooter.getYofMissileShoot(),
                            px, py // target location where the missile explodes
                    );
                    Main.gameData.friendFigures.add(m);
                }     

                try {
                    //Right click detected, initiate ranged attack
                    //shoot a missle at the mouse press location
                    fireballAudio();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                    Logger.getLogger(MouseController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Missile m = shooter.shootRanged(px,py, Shooter.getRangedWeapon());
                Main.gameData.friendFigures.add(m);

            }
        }
    }

    public void fireballAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        AudioInputStream fireball = null;
        try {
            //File file = new File("C:/Users/dinhn/Documents/GitHub/SHSBDA/PatakasWorld.wav");
            fireball = AudioSystem.getAudioInputStream(getClass().getResource("/resources/shooterFBwoosh.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(fireball);
            clip.start();
            fireball.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void swordAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        AudioInputStream swordSwing = null;
        try {
            swordSwing = AudioSystem.getAudioInputStream(getClass().getResource("/resources/swoosh.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(swordSwing);
            clip.start();
            swordSwing.close();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        shooter.setMouseMovedEvent(e);
    }
}
