package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import view.MainWindow;
import view.GamePanel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class GameData {

    private final int RADIUS = 6;
    public final List<GameFigure> enemyFigures;
    public final List<GameFigure> friendFigures;
    public final List<GameFigure> terrainFigures;
    public static Shooter shooter;
    ReentrantLock lock = new ReentrantLock();
    public static int multiplier = 0;
    private boolean levelComplete = false;
    private Image levelCompleteImage;
    private int level = 1;

    public GameData() {
        enemyFigures = new CopyOnWriteArrayList<>();
        friendFigures = new CopyOnWriteArrayList<>();
        terrainFigures = new CopyOnWriteArrayList<>();
        PowerUp p = new PowerUp(400, 480);

        /*
        try {
           
            levelCompleteImage = ImageIO.read(getClass().getResource("shooter.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
        **/
        // GamePanel.width, height are known when rendered. 
        // Thus, at this moment,
        // we cannot use GamePanel.width and height.
        shooter = new Shooter(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT / 2);

        //add figures based off current level
        if (level == 1) {
            friendFigures.add(shooter);
            friendFigures.add(p);

            enemyFigures.add(new BlinkMage((int) (Math.random() * 500), (int) Math.random() * 200));
            enemyFigures.add(new MeleeEnemy((int) (Math.random() * 500), (int) Math.random() * 200));
            enemyFigures.add(new SlowMage((int) (Math.random() * 500), (int) Math.random() * 200));
            enemyFigures.add(new SuicideEnemy((int) (Math.random() * 500), (int) Math.random() * 200));

            terrainFigures.add(new BlockTerrain(100, 100));
        }
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
            if (f.state == GameFigureState.STATE_DONE && f instanceof EnemyMissile) {
                removeEnemies.add(f);
            } else if (f.state == GameFigureState.STATE_DONE) {
                multiplier += 1;
                MainWindow.coins += multiplier;
                MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
                removeEnemies.add(f);
            }
        }
        enemyFigures.removeAll(removeEnemies);

        //Check if there are no enemies
        //if so, the level is complete
        //the next screen should be loaded to indicate level complete
        //after the next screen, shop should be loaded to let player spend currency on items
        if (enemyFigures.isEmpty()) {
            levelComplete = true;
            levelCheck();
        }

        for (GameFigure g : enemyFigures) {
            g.update();
        }

        //Blink Mage
        //-----------------------------------
        for (Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();) {
            GameFigure g = it.next();
            if (g.shootTimer == 20) {
                enemyFigures.add(new EnemyMissile(g.x, g.y));
            }
        }

        //Slow Mage
        //-----------------------------------
        for (Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();) {
            GameFigure slow = it.next();
            if (slow.slowTimer == 20) {
                enemyFigures.add(new EnemyMissileSlow(slow.x, slow.y));
            }
        }

        //Make EnemyMissileSlow actually slow
        //-----------------------------------        
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

    private void levelCheck() {

        //if current level is complete
        //increment level counter
        //clear friendfigures and all terrain from the screen
        //display message at top indicating level complete and how many coins aquired
        //display image on screen to indicate completion as well
        //Graphics2D g = null;
        if (levelComplete) {
            level++;
            friendFigures.clear();
            terrainFigures.clear();
            MainWindow.scoreText.setText("Level complete! You have "
                    + MainWindow.coins + " coins to spend at the shop.");
            // g.drawImage(levelCompleteImage, 0, 0, GamePanel.width, GamePanel.height, null);
        }
    }
}
