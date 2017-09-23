package controller;

import java.util.concurrent.TimeUnit;
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
            long startTime = System.currentTimeMillis();
            
            processCollisions();

            Main.gameData.update();
            Main.gamePanel.gameRender();
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
