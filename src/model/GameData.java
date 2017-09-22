package model;

import controller.Main;
import view.GamePanel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class GameData {

    private final int RADIUS = 6;
    public final List<GameFigure> enemyFigures;
    public final List<GameFigure> friendFigures;
    public final List<GameFigure> terrainFigures;
    public static Shooter shooter;
    ReentrantLock lock = new ReentrantLock();
    
    
    public GameData() {
        enemyFigures = new CopyOnWriteArrayList<>();
        friendFigures = new CopyOnWriteArrayList<>();
        terrainFigures = new CopyOnWriteArrayList<>();
        PowerUp  p = new PowerUp(400, 480);
        // GamePanel.width, height are known when rendered. 
        // Thus, at this moment,
        // we cannot use GamePanel.width and height.
        shooter = new Shooter(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 130);

        friendFigures.add(shooter);
        friendFigures.add(p);
        
        //enemyFigures.add(new BlinkMage((int)(Math.random() * 500), (int)Math.random()*200));
        enemyFigures.add(new BlinkMage((int)(Math.random() * 500), (int)Math.random()*200));
        enemyFigures.add(new MeleeEnemy((int)(Math.random() * 500), (int)Math.random()*200));
        enemyFigures.add(new SlowMage((int)(Math.random() * 500), (int)Math.random()*200));
 
        enemyFigures.add(new SuicideEnemy((int)(Math.random() * 500), (int)Math.random()*200));
        
        terrainFigures.add(new BlockTerrain(53, 145));
    }
    
  

    public void update() {

        // no enemy is removed in the program
        // since collision detection is not implemented yet.
        // However, if collision detected, simply set
        // f.state = GameFigure.STATE_DONE
        ArrayList<GameFigure> removeEnemies = new ArrayList<>();
        GameFigure f;
        for (int i = 0; i < enemyFigures.size(); i++) {
            f = enemyFigures.get(i);
            if (f.state == GameFigureState.STATE_DONE) {
                removeEnemies.add(f);
            }
        }
        enemyFigures.removeAll(removeEnemies);

        for (GameFigure g : enemyFigures) {
            g.update();
        }
        
        for(Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();)
            {
                GameFigure g = it.next();
                if(g.shootTimer == 20)
                enemyFigures.add(new EnemyMissile(g.x,g.y));
            }

        // missiles are removed if explosion is done
        ArrayList<GameFigure> removeFriends = new ArrayList<>();
        for (int i = 0; i < friendFigures.size(); i++) {
            f = friendFigures.get(i);
            if (f.state == GameFigureState.STATE_DONE) {
                removeFriends.add(f);
            }
        }
        friendFigures.removeAll(removeFriends);

        for (GameFigure g : friendFigures) {
            g.update();
        }
    }
}
