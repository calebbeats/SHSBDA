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
import model.BossSummon;
import model.BossSummonPet;
import model.EnemyMissileSummonPet;
import model.EnemyMissile;
import model.EnemyMissileWarlock;
import model.EnemyMissileSlow;
import model.SuicideEnemy;
import model.MeleeEnemy;
import model.SlowMage;
import model.GameData;
import model.IceTerrain;
import model.Melee;
import model.EnemyMissileMelee;
import model.Missile;
import model.MyBullet;
import model.Shield;
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
                    
            /* * * * * * * * * * * * * * * * * * * * * *         
            *   ____  _                 _              *
            *  / ___|| |__   ___   ___ | |_ ___ _ __   *
            *  \___ \| '_ \ / _ \ / _ \| __/ _ \ '__|  *
            *   ___) | | | | (_) | (_) | ||  __/ |     *
            *  |____/|_| |_|\___/ \___/ \__\___|_|     *                                       
            *                                          *
            * * * * * * * * * * * * * * * * * * * * * */
            
            //Shooter Takes Damage
            //------------------------------
            if (Main.gameData.shooter.getCollisionBox().intersects(s
                    .getCollisionBox()) && s.state != s.STATE_DYING) { //if shooter intersects any enemyfigure do this
                if (s instanceof EnemyMissile) {//this is if a hurtful enemy missile happens
                    s.goNextState();
                    GameData.multiplier = 0;
                    EnemyMissile.dealDamage();
                    
                } else if (s instanceof SuicideEnemy) {//do the enemy slow missile stuff here
                    s.goNextState();
                    GameData.multiplier = 0;
                    SuicideEnemy.dealDamage();
                    
                } else if (s instanceof EnemyMissileSlow) {//do the enemy slow missile stuff here
                    s.goNextState();
                    EnemyMissileSlow.dealDamage();
                    for (int i = 0; i < 5; i++) {
                        GameData.shooter.isSprint(FALSE);
                        System.out.println("Counter = " + i);
                    }
                    if (GameData.shooter.isSprint() == FALSE) {
                        System.out.println("Sprint is off");
                    }
                    
                } else if (s instanceof EnemyMissileMelee) {//this is where the enemy melee attacks would go
                    s.goNextState();
                    GameData.multiplier = 0;
                    EnemyMissileMelee.dealDamage();
                    
                } else if (s instanceof EnemyMissileWarlock) {
                    s.goNextState();
                    GameData.multiplier =0;
                    EnemyMissileWarlock.dealDamage();
                    
                } else if (s instanceof EnemyMissileSummonPet){
                    GameData.multiplier =0;
                    EnemyMissileSummonPet.dealDamage();
                }
            }
            
           /* * * * * * * * * * * * * * * * * * * * *          
           *    _____                               * 
           *   | ____|_ __   ___ _ __ ___  _   _    *
           *   |  _| | '_ \ / _ \ '_ ` _ \| | | |   *
           *   | |___| | | |  __/ | | | | | |_| |   *
           *   |_____|_| |_|\___|_| |_| |_|\__, |   *
           *                               |___/    *
            * * * * * * * * * * * * * * * * * * * * */
            
            //Enemy Takes Damage
            //------------------------------
            for (GameFigure f : Main.gameData.friendFigures) { //only process gamefigure collisionboxes if they are weapon or missile
                if (f instanceof Missile || f instanceof Melee || f instanceof MyBullet || f instanceof Shield) {
                    if (f.getCollisionBox().intersects(s.getCollisionBox()) /*&& f.state != f.STATE_DYING && s.state != s.STATE_DYING **/
                            && f.state != f.STATE_DONE
                            && s.state != s.STATE_DONE) {
                        
                        //Enemy -> SuicideEnemy
                        //------------------------------
                        if(s instanceof SuicideEnemy){
                            ((SuicideEnemy) s).takeDamage(Shooter.getWeaponPower());
                            if(((SuicideEnemy) s).getHealth() <= 0){
                                s.goNextState();
                            }
                            MainWindow.score += 5;
                        } else if (s instanceof MeleeEnemy){
                          //Enemy -> MeleeEnemy
                          //------------------------------
                            ((MeleeEnemy) s).takeDamage(Shooter.getWeaponPower());
                            if(((MeleeEnemy) s).getHealth() <= 0){
                                s.goNextState();
                            }
                            MainWindow.score += 5;
                        } else if (s instanceof SlowMage){
                          //Enemy -> SlowMage
                          //------------------------------
                            ((SlowMage) s).takeDamage(Shooter.getWeaponPower());
                            if(((SlowMage) s).getHealth() <= 0){ //if health goes to 0, it dies
                                s.goNextState();
                            }
                        } else if (s instanceof BlinkMage){
                          //Enemy -> BlinkMage
                          //------------------------------
                            ((BlinkMage) s).takeDamage(Shooter.getWeaponPower());
                            if(((BlinkMage) s).getHealth() <= 0){
                                s.goNextState();
                            }
                            MainWindow.score += 5;
                        } else if (s instanceof BossSummon) {
                          //Boss -> BossWarlock
                          //------------------------------
                            ((BossSummon) s).takeDamage(5);
                            if(((BossSummon)s).getHealth() <= 0){
                                s.goNextState();
                            }
                        } else if (s instanceof BossSummon) {
                          //Boss -> BossWarlockPet
                          //------------------------------
                            ((BossSummonPet) s).takeDamage(5);
                            if(((BossSummonPet)s).getHealth() <= 0){
                                s.goNextState();
                            }
                        } else{
                            s.goNextState();
                        }
                        f.goNextState();
                        //s.goNextState();                        
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
