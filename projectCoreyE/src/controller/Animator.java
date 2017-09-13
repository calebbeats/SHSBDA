package controller;

import java.awt.geom.Rectangle2D;
import model.GameFigure;

public class Animator implements Runnable
{
  public boolean running = true; //was set to true
  private final int FRAMES_PER_SECOND = 60;
  
  @Override
  public void run()
  {
    while (this.running)
    {
      long startTime = System.currentTimeMillis();
      
      processCollision();
      
      Main.gameData.update();
      Main.gamePanel.gameRender();
      Main.gamePanel.printScreen();
      
      long endTime = System.currentTimeMillis();
      int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                    - (int) (endTime - startTime);
      if (sleepTime > 0) {
        try
        {
          Thread.sleep(sleepTime);
        }
        catch (InterruptedException e) {}
      }
    }
    gameWon();
    System.exit(0);
  }
  
  private void gameWon(){
      boolean paused = true;
      while(paused){
          
      }
  }
  
  private void processCollision()
  {
      for (GameFigure friendFigure : Main.gameData.friendFigures) {
          Rectangle2D thisBox = ((GameFigure) friendFigure).getCollisionBox();
          for (GameFigure enemyFigure : Main.gameData.enemyFigures) {
              GameFigure enemy = (GameFigure) enemyFigure;
              if(thisBox.intersects(enemy.getCollisionBox()) && enemy.getHitPoints() > 0 && enemy.getHitPoints() < 5000){
                  enemy.addHitPoints();
              }
              else if(thisBox.intersects(enemy.getCollisionBox()) && enemy.getHitPoints() >= 5000){
                  enemy.state = 0;
              }
              else if (thisBox.intersects(enemy.getCollisionBox())) {
                  enemy.state = 0;                
              }
          }
      }
  }
}
