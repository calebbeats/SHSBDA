package model;


import java.awt.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import view.EndScreen;
import view.GameViewStrategy;
import view.StartScreen;
import view.InGame;
import view.InGame2;

public class GameData {

    private final int RADIUS = 6;
    public final List<GameFigure> enemyFigures;
    public final List<GameFigure> enemiesToBeAdded;
    public final List<GameFigure> friendFigures;
    public final List<GameFigure> friendsToBeAdded;
    public final PowerUp  p = new PowerUp(600, 600);
    public static Shooter shooter;
    public Image background;
    public GameViewStrategy strategy;
    public boolean inGame;
    public boolean inGame2;
  

    public GameData() {
        enemyFigures = Collections.synchronizedList(
                new ArrayList<GameFigure>() );
        enemiesToBeAdded = Collections.synchronizedList(
                new ArrayList<GameFigure>() );
        friendFigures = Collections.synchronizedList(
                new ArrayList<GameFigure>() );
        friendsToBeAdded = Collections.synchronizedList(
                new ArrayList<GameFigure>() );

        // GamePanel.width, height are known when rendered. 
        // Thus, at this moment,
        // we cannot use GamePanel.width and height.
        
        inGame = false;
        inGame2 = false;
        setStrategy(new StartScreen());
    }
    
    public void setStrategy(GameViewStrategy s)
    {
        this.strategy = s;
    }
  
    public void startGame()
    {
        setStrategy(new InGame2());
        inGame2 = true;
        inGame = false;
    }
    
    public void update() {
        
        if((shooter == null || enemyFigures.size() == 0) && inGame2 == true && inGame == false)
        {
            setStrategy(new InGame());
        }
        if((shooter == null || enemyFigures.size() == 0) && inGame == true)
        {
            setStrategy(new EndScreen());
        }
        
        // no enemy is removed in the program
        // since collision detection is not implemented yet.
        // However, if collision detected, simply set
        // f.state = GameFigure.STATE_DONE
        synchronized (enemyFigures) {
            ArrayList<GameFigure> remove = new ArrayList<>();
            GameFigure f;
            for (int i = 0; i < enemyFigures.size(); i++) {
                f = enemyFigures.get(i);
                if (f.state == GameFigure.STATE_DONE) {
                    remove.add(f);
                }
                
            }
            
            enemyFigures.removeAll(remove);
            System.out.println("Number of Enemies " + enemyFigures.size());
            
            for (GameFigure g : enemyFigures) {
                g.update();
                
            }
            
            for(Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();)
            {
                GameFigure g = it.next();
                if(g.shootTimer == 20)
                enemiesToBeAdded.add(new EnemyMissile(g.x,g.y));
            }
            System.out.println("Enemies added " + enemiesToBeAdded.size());
            System.out.println("Number of Enemies after addition " + enemyFigures.size());
            enemyFigures.addAll(enemiesToBeAdded);
            enemiesToBeAdded.removeAll(enemyFigures);
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
            System.out.println("Number of Good Guys after addition " + friendFigures.size());
            friendFigures.removeAll(remove);

            for (GameFigure g : friendFigures) {
                g.update();
            }
            
            
        }
    }
}
