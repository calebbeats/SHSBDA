package model;

import controller.HighscoreJAXB;
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
import view.GamePanel;

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
    private static PHASE phase;
    public static int MAXHEALTH = 5;
    public int check = 2;

    enum PHASE {

        ONE, TWO, THREE
    };

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
        level = Main.gameLevel;
        if (level == 1) {
           // terrainFigures.add(new BlockTerrain(250, 400, 100, 100));
            //terrainFigures.add(new SandTerrain(0, 50, 300, 300));
            //terrainFigures.add(new IceTerrain(300, 50, 300, 300));
            terrainFigures.add(new BlockTerrain(250, 400, 100, 100));
            friendFigures.add(shooter);

                        enemyFigures.add(new Boss((int) (Math.random() * 500), (int) Math.random() * 200, 15));

//            friendFigures.add(p);           
//            enemyFigures.add(new SuicideEnemy((int) (0), (int) (0)));      
//            enemyFigures.add(new BlinkMage(10, 10));
//            enemyFigures.add(new SlowMage(300, 100));
//            enemyFigures.add(new MeleeEnemy(500,500));
        } else if (level == 2) {
            terrainFigures.add(new SandTerrain(50, 50, 300, 450));
            friendFigures.add(shooter);           
            enemyFigures.add(new MeleeEnemy((int) (100), (int) (100)));  
            enemyFigures.add(new SlowMage((int) (0), (int) (100)));
            enemyFigures.add(new SlowMage((int) (200), (int) (0)));
            enemyFigures.add(new MeleeEnemy((int) (0), (int) (200)));
            enemyFigures.add(new SlowMage((int) (300), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (300)));
            enemyFigures.add(new SlowMage((int) (400), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (400)));
            enemyFigures.add(new SlowMage((int) (500), (int) (0)));

        } else if (level == 3) {
            friendFigures.add(shooter);
            shooter.setXY(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 125);
            terrainFigures.add(new IceTerrain(50, 50, 300, 300));
            enemyFigures.add(new SlowMage((int) (0), (int) (100)));
            enemyFigures.add(new BlinkMage((int) (100), (int) (0)));
            enemyFigures.add(new SlowMage((int) (200), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (200)));
            enemyFigures.add(new SlowMage((int) (300), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (300)));
            enemyFigures.add(new SlowMage((int) (400), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (400)));
            enemyFigures.add(new SlowMage((int) (500), (int) (0)));
        } else if (level == 4) {
            //THIS WILL BE A BOSS LEVEL, CHANGE!
            terrainFigures.add(new BlockTerrain(Main.WIN_WIDTH - 160, Main.WIN_HEIGHT - 270, 0, 0));
            shooter.setXY(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 125);
            friendFigures.add(shooter);
            enemyFigures.add(new BossSnake((int) (Math.random() * 500), (int) Math.random() * 200));
            //enemyFigures.add(new MeleeEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));
        } else if (level == 5) {
            //this is only a boss level temporarily 
            terrainFigures.add(new IceTerrain(Main.WIN_WIDTH - 160, Main.WIN_HEIGHT - 270, 0, 0));
            shooter.setXY(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 125);
            friendFigures.add(shooter);
            //enemyFigures.add(new Boss((int) (Math.random() * 500), (int) Math.random() * 200, 120));
            //enemyFigures.add(new MeleeEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));
            //enemyFigures.add(new Boss((int) (Math.random() * 500), (int) Math.random() * 200, 15));
            enemyFigures.add(new MeleeEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));
            enemyFigures.add(new MeleeEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));
            enemyFigures.add(new MeleeEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));
            
        } else if (level == 6) {
            terrainFigures.add(new IceTerrain(Main.WIN_WIDTH - 160, Main.WIN_HEIGHT - 270, 0, 0));
            shooter.setXY(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 125);
            friendFigures.add(shooter);
            enemyFigures.add(new BlinkMage((int) (Math.random() * 500), (int) (Math.random() * 200)));
            //   enemyFigures.add(new SuicideEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));

        } else if (level == 7) {
            terrainFigures.add(new BlockTerrain(Main.WIN_WIDTH / 3, Main.WIN_HEIGHT / 4, 0, 0));
            shooter.setXY(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 125);
            friendFigures.add(shooter);
            enemyFigures.add(new BlinkMage((int) (Math.random() * 500), (int) (Math.random() * 200)));
            enemyFigures.add(new SuicideEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));
            enemyFigures.add(new SuicideEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));
        } else if (level == 8) {
            //BOSS LEVEL, CHANGE
            terrainFigures.add(new IceTerrain(Main.WIN_WIDTH - 160, Main.WIN_HEIGHT - 270, 0, 0));
            shooter.setXY(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 125);
            friendFigures.add(shooter);
            //enemyFigures.add(new Boss(GamePanel.PWIDTH - 400, GamePanel.PHEIGHT / 2, 2 * 81, 2 * 81, 9));
            enemyFigures.add(new Boss((int) (Math.random() * 500), (int) Math.random() * 200, 15));
        } else if (level == 9) {
            terrainFigures.add(new BlockTerrain(Main.WIN_WIDTH / 3, Main.WIN_HEIGHT / 4, 0, 0));
            shooter.setXY(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 125);
            friendFigures.add(shooter);
            enemyFigures.add(new SuicideEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));
            enemyFigures.add(new SuicideEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));
            enemyFigures.add(new SuicideEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));
            enemyFigures.add(new SuicideEnemy((int) (Math.random() * 500), (int) (Math.random() * 200)));
            enemyFigures.add(new SlowMage((int) (Math.random() * 500), (int) (Math.random() * 200)));
            enemyFigures.add(new SlowMage((int) (Math.random() * 500), (int) (Math.random() * 200)));
            enemyFigures.add(new SlowMage((int) (Math.random() * 500), (int) (Math.random() * 200)));
            enemyFigures.add(new SlowMage((int) (Math.random() * 500), (int) (Math.random() * 200)));
        } else if (level == 10) {
            shooter.setXY(300, Main.WIN_HEIGHT);

            friendFigures.add(shooter); 

            terrainFigures.add(new IceTerrain(50, 50, 450, 450));
            enemyFigures.add(new SlowMage((int) (0), (int) (100)));
            enemyFigures.add(new BlinkMage((int) (100), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (200)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (300)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (400)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (500)));
            enemyFigures.add(new SlowMage((int) (500), (int) (100)));
            enemyFigures.add(new BlinkMage((int) (500), (int) (200)));
            enemyFigures.add(new SlowMage((int) (500), (int) (300)));
            enemyFigures.add(new SuicideEnemy((int) (500), (int) (400)));
            enemyFigures.add(new SlowMage((int) (500), (int) (500)));
            enemyFigures.add(new SuicideEnemy((int) (100), (int) (500)));
            enemyFigures.add(new SlowMage((int) (200), (int) (500)));
            enemyFigures.add(new SuicideEnemy((int) (300), (int) (500)));
            enemyFigures.add(new SlowMage((int) (400), (int) (500)));
            enemyFigures.add(new BlinkMage((int) (500), (int) (500)));
        } else if (level == 11) {
            terrainFigures.add(new SandTerrain(0, 50, 600, 500));
            friendFigures.add(shooter);

            enemyFigures.add(new SuicideEnemy((int) (0), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (100)));
            enemyFigures.add(new SuicideEnemy((int) (100), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (200), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (200)));
            enemyFigures.add(new SuicideEnemy((int) (300), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (300)));
            enemyFigures.add(new SuicideEnemy((int) (400), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (400)));
            enemyFigures.add(new SuicideEnemy((int) (500), (int) (0)));
            enemyFigures.add(new SuicideEnemy((int) (0), (int) (500)));
            enemyFigures.add(new SuicideEnemy((int) (500), (int) (100)));
            enemyFigures.add(new SuicideEnemy((int) (500), (int) (200)));
            enemyFigures.add(new SuicideEnemy((int) (500), (int) (300)));
            enemyFigures.add(new SuicideEnemy((int) (500), (int) (400)));
            enemyFigures.add(new SuicideEnemy((int) (500), (int) (500)));
            enemyFigures.add(new SuicideEnemy((int) (100), (int) (500)));
            enemyFigures.add(new SuicideEnemy((int) (200), (int) (500)));
            enemyFigures.add(new SuicideEnemy((int) (300), (int) (500)));
            enemyFigures.add(new SuicideEnemy((int) (400), (int) (500)));
            enemyFigures.add(new SuicideEnemy((int) (500), (int) (500)));
        } else if (level == 12) {
            //BOSS LEVEL CHANGE
            terrainFigures.add(new IceTerrain(Main.WIN_WIDTH - 160, Main.WIN_HEIGHT - 270, 0, 0));
            shooter.setXY(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 125);
            friendFigures.add(shooter);
            enemyFigures.add(new BossSummon((int) (Math.random() * 500), (int) Math.random() * 200));
            
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
                    && f instanceof EnemyMissileMelee && f instanceof EnemyMissileSummonPet
                    && f instanceof EnemyMissileWarlock) {
                removeEnemies.add(f);
            } else if (f.state == GameFigureState.STATE_DONE
                    && f instanceof Boss) {
                removeEnemies.add(f);
                // while(check < 2){

                if (check == 2) {
                    enemyFigures.add(new Boss((int) (Math.random() * 500), (int) Math.random() * 200, 15));
                    enemyFigures.add(new Boss((int) (Math.random() * 500), (int) Math.random() * 200, 15));

                    check -= 1;
                } else if (check == 1) {
                    enemyFigures.add(new Boss((int) (Math.random() * 500), (int) Math.random() * 200, 15));
                    //enemyFigures.add(new FlyEnemy((int) (Math.random() * 700), (int) Math.random() * 300, 15));
                    //
                    enemyFigures.add(new FlyBossInitialize((int) (Math.random() * 500), (int) Math.random() * 200, 15));
                    enemyFigures.add(new FlyBossInitialize((int) (Math.random() * 500), (int) Math.random() * 200, 15));
 
                    check -= 1;
                } else {
                    removeEnemies.add(f);
                }

                // check--;
                // }
            }else if (f.state == GameFigureState.STATE_DONE) {

                multiplier += 1;
                MainWindow.coins += multiplier;
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
                enemyFigures.add(new EnemyMissileMelee(swing.x, swing.y));
            }

            if (swing.petSwingTimer == 20) {
                enemyFigures.add(new EnemyMissileSummonPet(swing.x, swing.y));
            }
        }

        //BOSS -> Warlock
        //-----------------------------------
        for (Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();) {
            GameFigure g = it.next();
            if (g.bossTimer == 50) {
                enemyFigures.add(new EnemyMissileWarlock(g.x, g.y));
            }

            //BOSS -> Summon Pet
            //---------------------------------
            if (g.bossTimer == 25 || g.bossTimer == 75) {
                enemyFigures.add(new BossSummonPet(g.x, g.y));
            }
        }

        //BOSS -> Snake
        //-----------------------------------
        for (Iterator<GameFigure> it = enemyFigures.iterator(); it.hasNext();) {
            GameFigure g = it.next();
            if (g.poisonTimer == 150
                    || g.poisonTimer == 175
                    || g.poisonTimer == 200
                    || g.poisonTimer == 250
                    || g.poisonTimer == 275
                    || g.poisonTimer == 300
                    || g.poisonTimer == 325) {
                enemyFigures.add(new EnemyMissilePoison(g.x, g.y));
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

    private void levelCheck() throws IOException {
        //if current level is complete
        //increment level counter
        //clear powerups and all terrain from the screen
        //display message at top indicating level complete and how many coins aquired
        if (levelComplete) {
            if (playerDead || level == 13) {
                Main.gameInitialize();
                if (playerDead) {
                    Main.gameLevel = 1;
                    Main.gameState = Main.GameState.GameOver;
                } else {
                    Main.gameState = Main.GameState.Winner;
                }
                Main.gameLevel = 1;
                HighscoreJAXB.checkScore();
            } else {
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

    public static PHASE getphase() {
        return phase;
    }

    public static void setphase(PHASE p) {
        phase = p;
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

    void addFriendlyFigure(GameFigure g) {
        friendFigures.add(g);
    }
}
