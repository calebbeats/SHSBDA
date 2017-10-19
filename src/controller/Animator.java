package controller;

import java.io.IOException;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.GameFigure;
import model.Shooter;
import model.BlinkMage;
import model.EnemyMissile;
import model.EnemyMissileSlow;
import model.SuicideEnemy;
import model.MeleeEnemy;
import model.SlowMage;
import model.GameData;
import static model.GameData.shooter;
import model.Melee;
import model.Missile;
import view.MainWindow;

public class Animator implements Runnable {

    public boolean running = false;
    private final int FRAMES_PER_SECOND = 50;
  
    @Override
    public void run() {

        while (running) {
            if (!Main.isPaused) {//as long as game is not paused, update everything
                long startTime = System.currentTimeMillis();

                processCollisions();

                try {
                    Main.gameData.update();
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Main.gamePanel.gameRender();
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException ex) {
                    Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
                }
                Main.gamePanel.printScreen();

                long endTime = System.currentTimeMillis();
                int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                        - (int) (endTime - startTime);

                if (sleepTime > 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(sleepTime);
                    } catch (InterruptedException e) {

                    }
                }
            } else {
//                MainWindow.resumeGame.setEnabled(true);
                long startTime = System.currentTimeMillis();
                long endTime = System.currentTimeMillis();
                int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                        - (int) (endTime - startTime);

                if (sleepTime > 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(sleepTime);
                    } catch (InterruptedException e) {

                    }
                }
                //bring up pause menu here
                //Main.gameData.update();
            }
        }

        try {
            Thread.sleep(1500);
            Thread.currentThread().interrupt();
            System.exit(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processCollisions() {
        // detect collisions between friendFigure and enemyFigures
        // if detected, mark it as STATE_DONE, so that
        // they can be removed at update() method
        for (GameFigure s : Main.gameData.enemyFigures) {
            if (Main.gameData.shooter.getCollisionBox().intersects(s.getCollisionBox()) && s.state != s.STATE_DYING) { //if shooter intersects any enemyfigure do this
                if(s instanceof EnemyMissile || s instanceof SuicideEnemy){//this is if a hurtful enemy missile happens
                    s.goNextState();
                    GameData.multiplier = 0;
                    GameData.shooter.takeDamage(20);
                }
                
                //Slow Missile
                //------------------------------
                else if(s instanceof EnemyMissileSlow){
                    shooter.isSprint(FALSE);
                    System.out.println("Sprint Stopped!");
                    shooter.takeDamage(1);
                }
                //this is where the enemy melee attacks would go
                s.goNextState();                                                                                   
            }

            for (GameFigure f : Main.gameData.friendFigures) { //only process gamefigure collisionboxes if they are weapon or missile
                if(f instanceof Missile || f instanceof Melee){
                    if (f.getCollisionBox().intersects(s.getCollisionBox()) && f.state != f.STATE_DYING && s.state != s.STATE_DYING
                        && f.state != f.STATE_DONE && s.state != s.STATE_DONE ) {
                        f.goNextState();
                        s.goNextState();
                        MainWindow.score += 5;
                        MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
                    }
                }                
            }

            //detection for enemy attacks hitting terrain
            for (GameFigure t : Main.gameData.terrainFigures) {
                if (s.getCollisionBox().intersects(t.getCollisionBox()) && !((s instanceof BlinkMage) || (s instanceof SuicideEnemy) || (s instanceof MeleeEnemy) || (s instanceof SlowMage))) {
                    s.goNextState();
                }
            }
        }
        //detection for freindly attacks hitting terrain
        for (GameFigure m : Main.gameData.friendFigures) {
            for (GameFigure t : Main.gameData.terrainFigures) {
                if (m.getCollisionBox().intersects(t.getCollisionBox()) && !(m instanceof Shooter)) {
                    m.goNextState();
                }
            }
        }
    }
}
