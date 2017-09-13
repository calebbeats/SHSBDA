package controller;

import model.FireballPower;
import model.GameFigure;
import view.EndScreen;

public class Animator implements Runnable {

    public boolean running = true;
    private final int FRAMES_PER_SECOND = 20;

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
                    Thread.sleep(sleepTime); // ms
                } catch (InterruptedException e) {

                }
            }
        }
        System.exit(0);
    }
    
    private synchronized void processCollisions() {
        
       
        for (GameFigure s : Main.gameData.enemyFigures) {
            if(Main.gameData.shooter.getCollisionBox().intersects(s.getCollisionBox()) && s.state != s.STATE_DYING )
            {
              s.goNextState();
              
              if(s.getClass() == Main.gameData.p.getClass())
              {
                  Main.gameData.shooter.weapon= new FireballPower(Main.gameData.shooter.weapon);
              }
              else
              {
                  Main.gameData.setStrategy(new EndScreen());
              }
            }
       
            for(GameFigure f : Main.gameData.friendFigures)
            {
                if(f.getCollisionBox().intersects(s.getCollisionBox()) && f.state != f.STATE_DYING && s.state != s.STATE_DYING
                        && f.state != f.STATE_DONE && s.state != s.STATE_DONE)   
                {
                    f.goNextState();
                    s.goNextState();
                }
            }
        }
      }
    }


