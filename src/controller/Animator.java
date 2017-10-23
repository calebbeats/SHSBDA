package controller;

import java.io.IOException;
import static java.lang.Boolean.FALSE;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.GameFigure;
import model.Shooter;
import model.BlinkMage;
import model.BlockTerrain;
import model.BossWarlockPetAttack;
import model.EnemyMissile;
import model.EnemyMissileBoss;
import model.EnemyMissileSlow;
import model.SuicideEnemy;
import model.MeleeEnemy;
import model.SlowMage;
import model.GameData;
import model.IceTerrain;
import model.Melee;
import model.MeleeEnemyAttack;
import model.Missile;
import model.MyBullet;
import view.MainWindow;

public class Animator implements Runnable {

    private final int FRAMES_PER_SECOND = 50;

    @Override
    public void run() {
        while (true) {
            switch (Main.gameState) {
                case Start:
                case Pause:
                case GameOver:
                case LevelComplete:
                case Shop:
                    gamePanelRender();
                    break;
                case Run:
                    long startTime = System.currentTimeMillis();

                    processCollisions();

                    try {
                        Main.gameData.update();
                    } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(Animator.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }

                    gamePanelRender();

                    long endTime = System.currentTimeMillis();
                    int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                            - (int) (endTime - startTime);

                    if (sleepTime > 0) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            Logger.getLogger(Animator.class.getName())
                                    .log(Level.SEVERE, null, e);
                        }
                    }
                    break;
                case Quit:
                    gamePanelRender();
                    Thread.currentThread().interrupt();
                    System.exit(0);
                    break;
            }
        }
    }

    private void processCollisions() {
        // detect collisions between friendFigure and enemyFigures
        // if detected, mark it as STATE_DONE, so that
        // they can be removed at update() method
        for (GameFigure s : Main.gameData.enemyFigures) {
            if (Main.gameData.shooter.getCollisionBox().intersects(s
                    .getCollisionBox()) && s.state != s.STATE_DYING) { //if shooter intersects any enemyfigure do this
                if (s instanceof EnemyMissile) {//this is if a hurtful enemy missile happens
                    s.goNextState();
                    GameData.multiplier = 0;
                    GameData.shooter.takeDamage(20);
                } else if (s instanceof SuicideEnemy) {//do the enemy slow missile stuff here
                    s.goNextState();
                    GameData.multiplier = 0;
                    GameData.shooter.takeDamage(100);
                } else if (s instanceof EnemyMissileSlow) {//do the enemy slow missile stuff here
                    s.goNextState();
                    GameData.shooter.takeDamage(1);
                    for (int i = 0; i < 5; i++) {
                        GameData.shooter.isSprint(FALSE);
                        System.out.println("Counter = " + i);
                    }
                    if (GameData.shooter.isSprint() == FALSE) {
                        System.out.println("Sprint is off");
                    }
                } else if (s instanceof MeleeEnemyAttack) {//this is where the enemy melee attacks would go
                    s.goNextState();
                    GameData.multiplier = 0;
                    GameData.shooter.takeDamage(20);
                } else if (s instanceof EnemyMissileBoss) {
                    s.goNextState();
                    GameData.multiplier =0;
                    GameData.shooter.takeDamage(100);                    
                } else if (s instanceof BossWarlockPetAttack){
                    GameData.multiplier =0;
                    GameData.shooter.takeDamage(20);                     
                }
            }

            for (GameFigure f : Main.gameData.friendFigures) { //only process gamefigure collisionboxes if they are weapon or missile
                if (f instanceof Missile || f instanceof Melee || f instanceof MyBullet) {
                    if (f.getCollisionBox().intersects(s.getCollisionBox()) /*&& f.state != f.STATE_DYING && s.state != s.STATE_DYING **/
                            && f.state != f.STATE_DONE
                            && s.state != s.STATE_DONE) {
                        f.goNextState();
                        s.goNextState();
                        MainWindow.score += 5;
                        MainWindow.scoreText.setText("Score: "
                                + MainWindow.score + " || Coins: "
                                + MainWindow.coins);
                    }
                }
            }

            //detection for enemy attacks hitting terrain
            for (GameFigure t : Main.gameData.terrainFigures) {
                if (s.getCollisionBox().intersects(t.getCollisionBox())
                        && !((s instanceof BlinkMage)
                        || (s instanceof SuicideEnemy)
                        || (s instanceof MeleeEnemy) || (s instanceof SlowMage))
                        && (t instanceof BlockTerrain)) {
                    s.goNextState();
                }
            }
        }
        //detection for freindly attacks hitting terrain
        for (GameFigure m : Main.gameData.friendFigures) {
            for (GameFigure t : Main.gameData.terrainFigures) {
                if (m.getCollisionBox().intersects(t.getCollisionBox())) {
                    if (!(m instanceof Shooter) && (t instanceof BlockTerrain)) {
                        m.goNextState();
                    }
                }
            }
        }
    }

    private void gamePanelRender() {
        try {
            Main.gamePanel.gameRender();
        } catch (IOException | UnsupportedAudioFileException
                | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(Animator.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        Main.gamePanel.printScreen();
    }
}
