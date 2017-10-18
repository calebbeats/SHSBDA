package model;

import controller.ButtonListener;
import controller.Main;
import java.awt.Image;
import java.io.IOException;
import view.MainWindow;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
            levelCompleteImage = ImageIO.read(getClass().getResource("view/levelComplete.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open levelComplete.png");
           System.exit(-1);
        }
        **/
        
        // GamePanel.width, height are known when rendered. 
        // Thus, at this moment,
        // we cannot use GamePanel.width and height.
        shooter = new Shooter(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT / 2);

        //load enemies, terrain, and powerups based on current level
        if (level == 1) {
        friendFigures.add(shooter);
        friendFigures.add(p);
        enemyFigures.add(new BlinkMage((int)(Math.random() * 500), (int)Math.random()*200));
        enemyFigures.add(new MeleeEnemy((int)(Math.random() * 500), (int)Math.random()*200));
        enemyFigures.add(new SlowMage((int)(Math.random() * 500), (int)Math.random()*200));
        enemyFigures.add(new SuicideEnemy((int)(Math.random() * 500), (int)Math.random()*200));
        terrainFigures.add(new BlockTerrain(200, 200));
        }
    }

    public void update() throws UnsupportedAudioFileException, IOException {

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
            } 
            else if (f.state == GameFigureState.STATE_DONE) {
                multiplier += 1;
                MainWindow.coins += multiplier;
                MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
                removeEnemies.add(f);
                try {
                    audio();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        enemyFigures.removeAll(removeEnemies);

        if(enemyFigures.isEmpty()) { //if enemies are dead so set button enabled
            disableShop();
            levelComplete = true;
            levelCheck();
        }
        else
            MainWindow.shopButton.setEnabled(false);
        
        for (GameFigure g : enemyFigures) {
            g.update();
        }

        //Blink Mage
        //-----------------------------------
        for(Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();) {
            GameFigure g = it.next();
            if(g.shootTimer == 20)
            enemyFigures.add(new EnemyMissile(g.x,g.y));
        }
        
        //Slow Mage
        //-----------------------------------
        for(Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();) {
            GameFigure slow = it.next();
            if(slow.slowTimer == 20)
                enemyFigures.add(new EnemyMissileSlow(slow.x,slow.y));
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
    
    private void disableShop(){
        if(ButtonListener.shop.isVisible())
            MainWindow.shopButton.setEnabled(false);        
        else
            MainWindow.shopButton.setEnabled(true);
    }
    
    private void levelCheck() {
        //if current level is complete
        //increment level counter
        //clear powerups and all terrain from the screen
        //display message at top indicating level complete and how many coins aquired
        //display image on screen to indicate completion as well
        if (levelComplete) {
            level++;
            GameFigure f;
            ArrayList<GameFigure> removePowerUps = new ArrayList<>();
            for (int i = 0; i < friendFigures.size(); i++) {
                f = friendFigures.get(i);
                if (f instanceof PowerUp) {
                    removePowerUps.add(f);
                }
            }
            friendFigures.removeAll(removePowerUps);
            terrainFigures.clear();
            MainWindow.scoreText.setText("Level complete! You have "
                    + MainWindow.coins + " coins to spend at the shop.");
            // g.drawImage(levelCompleteImage, 0, 0, GamePanel.width, GamePanel.height, null);
        }
    }

    public void audio() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        AudioInputStream stream = null;
        try {
            //File file = new File("C:/Users/dinhn/Documents/GitHub/SHSBDA/PatakasWorld.wav");
            stream = AudioSystem.getAudioInputStream(getClass().getResource("explosion.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            stream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
