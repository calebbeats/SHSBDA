package model;

import controller.Main;
import view.GamePanel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import view.MainWindow;

public class GameData {

    private final int RADIUS = 6;
    public final List<GameFigure> enemyFigures;
    public final List<GameFigure> friendFigures;
    public static Shooter shooter;
    private static int switchOff = 1;
    public static int score = 0;
    public static int enemyCount = 0;
    public static int enemyKilled = 0;
    public boolean levelTwo = false;

    public GameData() {
        enemyFigures = Collections.synchronizedList(
                new ArrayList<GameFigure>() );
        friendFigures = Collections.synchronizedList(
                new ArrayList<GameFigure>() );

        shooter = new Shooter(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 130);
        //append the Decorator to shooter
        

        friendFigures.add(shooter);
    }
    
    public void callEnemies(){
        double random = Math.random() * 100;
        if(random >= 50)       
            this.add(1);        
        else
            this.addSatellite();
    }
    
    public void addSatellite(){
       enemyFigures.add(new Satellite(
      (float)(Math.random() * GamePanel.width + 10), 
      (float)(Math.random() * GamePanel.height - 10)));      
    }

    public void addBoss(){
       enemyFigures.add(new Boss(80,80));   
    }
    
    public void add(int n) {
        synchronized (enemyFigures) {
            for (int i = 0; i < n; i++) {
                float red = (float) Math.random();
                float green = (float) Math.random();
                float blue = (float) Math.random();
                // adjust if too dark since the background is black
                if (red < 0.5) {
                    red += 0.2;
                }
                if (green < 0.5) {
                    green += 0.2;
                }
                if (blue < 0.5) {
                    blue += 0.2;
                }
                enemyFigures.add(new Asteroid(
                        (int) (Math.random() * GamePanel.width),
                        (int) (Math.random() * GamePanel.height),
                        RADIUS,
                        new Color(red, green, blue)));
            }
        }
    }

    public void update() {
        synchronized (enemyFigures) {         
            
            //display the enemy kill count
            MainWindow.gameInfo.setText("Enimies Killed: " + enemyKilled);
            
            //random count decides when enemies appear on screen
            double randomCall = Math.random() * 10000;
            if(randomCall <= 100 && enemyCount < 15){
                this.callEnemies();
                enemyCount++;
            }
            else if(enemyCount == 15 && score == 1500){
                addBoss();
                enemyCount++;
               levelTwo = true;
            }
            
            ArrayList<GameFigure> remove = new ArrayList<>();    
            GameFigure f;
            for (int i = 0; i < enemyFigures.size(); i++) {

                f = enemyFigures.get(i);
                
                if (f.state == GameFigure.STATE_DONE) {

                    f.goNextState();
                    //adds the destroyed enemy to the arraylist 'remove'
                    remove.add(f); 

                    if(switchOff > 15){ //using this to keep death animation on screen for longer time
                        switchOff = 0;
                    }
                    else if(switchOff >= 14){
                        score += 100;
                        enemyKilled++;
                        enemyFigures.removeAll(remove);
                        switchOff = 1;
                    }
                    if(enemyKilled == 16){
                        Main.gamePanel.gameWon = true;
                    }
                }  
            }          
            switchOff++;
            for (GameFigure g : enemyFigures) {
                g.update();
            }
            
        }        
        // missiles are removed if explosion is done
        synchronized (friendFigures) {
            ArrayList<GameFigure> remove = new ArrayList<>();
            GameFigure f;
            for (int i = 0; i < friendFigures.size(); i++) {
                f = friendFigures.get(i);
                if (f.state == GameFigure.STATE_DONE) {
                    remove.add(f);
                }
            }
            friendFigures.removeAll(remove);

            for (GameFigure g : friendFigures) {
                g.update();
            }
        }
    }
}
