package controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.GameFigure;
import model.Shooter;
import model.BlinkMage;
import model.SuicideEnemy;
import model.MeleeEnemy;
import model.SlowMage;
import model.GameFigureState;
import model.GameData;
import view.MainWindow;

public class Animator implements Runnable {

    public boolean running = true;
    private final int FRAMES_PER_SECOND = 50;
    
    @Override
    public void run() {

        while (running) {
            if(!Main.isPaused){//as long as game is not paused, update everything
                long startTime = System.currentTimeMillis();
            
                processCollisions();

                Main.gameData.update();
                try {
                    Main.gamePanel.gameRender();
                } catch (IOException ex) {
                    Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
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
            }else{
                MainWindow.resumeGame.setEnabled(true);
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
        System.exit(0);
    }
    
    private void processCollisions() {
        // detect collisions between friendFigure and enemyFigures
        // if detected, mark it as STATE_DONE, so that
        // they can be removed at update() method
        for (GameFigure s : Main.gameData.enemyFigures) {
            if(Main.gameData.shooter.getCollisionBox().intersects(s.getCollisionBox()) && s.state != s.STATE_DYING )
            {
              s.goNextState();              
              GameData.multiplier = 0;
              GameData.shooter.takeDamage(20);
            
            }
       
            for(GameFigure f : Main.gameData.friendFigures)
            {
                if(f.getCollisionBox().intersects(s.getCollisionBox()) && f.state != f.STATE_DYING && s.state != s.STATE_DYING
                        && f.state != f.STATE_DONE && s.state != s.STATE_DONE)   
                {
                    f.goNextState();
                    s.goNextState();
                    MainWindow.score += 5;
                    MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
                }
            }
            //detection for enemy attacks hitting terrain
            for(GameFigure t : Main.gameData.terrainFigures){
                if(s.getCollisionBox().intersects(t.getCollisionBox()) && !((s instanceof BlinkMage) || (s instanceof SuicideEnemy) || (s instanceof MeleeEnemy) || (s instanceof SlowMage))){
                    s.goNextState();
                }
            }
        }
        //detection for freindly attacks hitting terrain
        for (GameFigure m : Main.gameData.friendFigures){
            for(GameFigure t : Main.gameData.terrainFigures){
                if(m.getCollisionBox().intersects(t.getCollisionBox()) && !(m instanceof Shooter)){
                    m.goNextState();
                }
            }
        }
    }
}
