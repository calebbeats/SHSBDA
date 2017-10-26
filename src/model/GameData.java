package model;

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
    public static MyBullet myBullet;
    ReentrantLock lock = new ReentrantLock();
    public static int multiplier = 0;
    private boolean levelComplete = false;
    private boolean playerDead = false;
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
            terrainFigures.add(new IceTerrain(Main.WIN_WIDTH - 160, Main.WIN_HEIGHT - 270, 125, 125));
            friendFigures.add(shooter);
            
            //TEST BLOCK FOR NORMAL
            //------------------------------
                //enemyFigures.add(new BlinkMage((int) (Math.random() * 500), (int) Math.random() * 200));
                //enemyFigures.add(new MeleeEnemy((int) (Math.random() * 500), (int) Math.random() * 200));
                //enemyFigures.add(new SlowMage((int) (Math.random() * 500), (int) Math.random() * 200));
                //enemyFigures.add(new SuicideEnemy((int) (Math.random() * 500), (int) Math.random() * 200));
            
            //TEST BLOCK FOR BOSS
            //------------------------------
                enemyFigures.add(new BossWarlock((int) (Math.random() * 500), (int) Math.random() * 200));                  
                //enemyFigures.add(new BossWarlockPet((int) (Math.random() * 500), (int) Math.random() * 200));                  
        }
        /*
        if((level % 4) == 0){
            enemyFigures.add(new BossWarlock((int) (Math.random() * 500), (int) Math.random() * 200));                  
        }
        */
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
            if (f.state == GameFigureState.STATE_DONE
                    && f instanceof EnemyMissile && f instanceof EnemyMissileSlow 
                    && f instanceof MeleeEnemyAttack && f instanceof BossWarlockPetAttack) {
                removeEnemies.add(f);
            } else if (f.state == GameFigureState.STATE_DONE) {
                multiplier += 1;
                MainWindow.coins += multiplier;
                MainWindow.scoreText.setText("Score: "
                        + MainWindow.score + " || Coins: " + MainWindow.coins);
                removeEnemies.add(f);
                try {
                    audio();
                } catch (LineUnavailableException | InterruptedException ex) {
                    Logger.getLogger(GameData.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        }
        enemyFigures.removeAll(removeEnemies);
        
        if (enemyFigures.isEmpty()) { //if enemies are dead set shop button enabled
            levelComplete = true;
            levelCheck();
        }

        for (GameFigure g : enemyFigures) {
            g.update();
        }
        
        /* * * * * * * * * * * * * * * * * 
        *  __  __ _         _ _            *
        * |  \/  (_)       (_) |           *
        * | \  / |_ ___ ___ _| | ___  ___  *
        * | |\/| | / __/ __| | |/ _ \/ __| *
        * | |  | | \__ \__ \ | |  __/\__ \ *
        * |_|  |_|_|___/___/_|_|\___||___/ *
        *                                  *
        * * * * * * * * * * * * * * * * * */
                                          
        //NORMAL -> Blink Mage
        //-----------------------------------
        for (Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();) {
            GameFigure g = it.next();
            if (g.shootTimer == 20) {
                enemyFigures.add(new EnemyMissile(g.x, g.y));
            }
        }

        //NORMAL -> Slow Mage
        //-----------------------------------
        for (Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();) {
            GameFigure slow = it.next();
            if (slow.slowTimer == 20) {
                enemyFigures.add(new EnemyMissileSlow(slow.x, slow.y));
            }
        }

        // NORMAL -> Melee
        //------------------------------
        for (Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();) {
            GameFigure swing = it.next();
            if (swing.swingTimer == 20) {
                enemyFigures.add(new MeleeEnemyAttack(swing.x, swing.y));
            }
            
            if (swing.petSwingTimer == 20){
                enemyFigures.add(new BossWarlockPetAttack(swing.x, swing.y));
            }
        }

        //BOSS -> Warlock
        //-----------------------------------
        for (Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();) {
            GameFigure g = it.next();
            if (g.bossTimer == 50) {
                enemyFigures.add(new EnemyMissileBoss(g.x, g.y));
            } 
            
            //BOSS -> Summon Pet
            //---------------------------------
            if (g.bossTimer == 20) {
                enemyFigures.add(new BossWarlockPet(g.x, g.y));
            }          
        }
        
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
        * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */ 
        
        
        // attacks and shooter removed if STATE_DONE
        ArrayList<GameFigure> removeFriends = new ArrayList<>();
        for (int i = 0; i < friendFigures.size(); i++) {
            f = friendFigures.get(i);
            if (f.state == GameFigureState.STATE_DONE) {
                removeFriends.add(f);
                if (f instanceof Shooter) {
                    playerDead = true;
                    levelComplete = true;
                    levelCheck();
                }
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
        //clear powerups and all terrain from the screen
        //display message at top indicating level complete and how many coins aquired
        if (levelComplete) {
            if (playerDead) {
                Main.gameInitialize();
                Main.gameState = Main.GameState.GameOver;
            } else {
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
                Main.gameState = Main.GameState.LevelComplete;
            }
        }
    }

    public void audio() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        AudioInputStream stream = null;
        try {
            //File file = new File("C:/Users/dinhn/Documents/GitHub/SHSBDA/PatakasWorld.wav");
            stream = AudioSystem.getAudioInputStream(getClass().getResource("/resources/explosion8bit.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            stream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
