package controller;

import java.io.IOException;
import static java.lang.Boolean.FALSE;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.GameFigure;
import model.Shooter;
import model.BlinkMage;
import model.BlockTerrain;
import model.Boss;
import model.BossSnake;
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
import model.Melee;
import model.EnemyMissileMelee;
import model.EnemyMissilePoison;
import static model.GameFigure.DAMAGE;
import model.ItemExplosion;
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
                    MainWindow.coins = 0;
                    MainWindow.score = 0;
                    Main.gameLevel = 1;
                case Difficulty:
                case Pause:
                case HighScore:
                case Winner:
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
        Main.quatree.clear();
        Main.gameData.friendFigures.forEach(friendFigure -> {
            Main.quatree.insert(friendFigure);
        });
        Main.gameData.enemyFigures.forEach(enemyFigure -> {
            Main.quatree.insert(enemyFigure);
        });
        Main.gameData.terrainFigures.forEach(terrainFigure -> {
            Main.quatree.insert(terrainFigure);
        });
        List<GameFigure> returnCollidableFigures = new ArrayList<>();
        Main.gameData.terrainFigures.forEach(terrainFigure -> {
            Main.quatree.retrieve(returnCollidableFigures, terrainFigure);

            // detection for enemy attacks and friendly attacks hitting terrain
            returnCollidableFigures.forEach(collidableFigure -> {
                if (collidableFigure.getCollisionBox().intersects(terrainFigure.getCollisionBox())) {
                    if (!(collidableFigure instanceof Shooter || collidableFigure instanceof BlinkMage
                            || collidableFigure instanceof SuicideEnemy
                            || collidableFigure instanceof MeleeEnemy || (collidableFigure instanceof SlowMage))
                            && terrainFigure instanceof BlockTerrain) {
                        collidableFigure.goNextState();
                    }
                }
            });
        });
        returnCollidableFigures.clear();
        Main.gameData.friendFigures.forEach(friendFigure -> {
            Main.quatree.retrieve(returnCollidableFigures, friendFigure);

            returnCollidableFigures.stream().filter(collidableFigure -> Main.gameData.enemyFigures.contains(collidableFigure)).forEach(collidableFigure -> {

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
                if (Main.gameData.shooter.getCollisionBox().intersects(collidableFigure
                        .getCollisionBox()) && collidableFigure.state != collidableFigure.STATE_DYING && collidableFigure.state != collidableFigure.STATE_DONE) { //if shooter intersects any enemyfigure do this
                    if (collidableFigure instanceof EnemyMissile) {//this is if a hurtful enemy missile happens
                        collidableFigure.goNextState();
                        collidableFigure.goNextState();
                        GameData.multiplier = 0;
                        EnemyMissile.dealDamage();

                    } else if (collidableFigure instanceof Boss) {
                        collidableFigure.goNextState();
                        GameData.multiplier = 0;
                        GameData.shooter.takeDamage(200);
                    } else if (collidableFigure instanceof SuicideEnemy) {//do the enemy slow missile stuff here
                        collidableFigure.goNextState();
                        GameData.multiplier = 0;
                        SuicideEnemy.dealDamage();

                    } else if (collidableFigure instanceof EnemyMissileSlow) {//do the enemy slow missile stuff here
                        collidableFigure.goNextState();
                        EnemyMissileSlow.dealDamage();
                        for (int i = 0; i < 5; i++) {
                            GameData.shooter.isSprint(FALSE);
                            System.out.println("Counter = " + i);
                        }
                        if (GameData.shooter.isSprint() == FALSE) {
                            System.out.println("Sprint is off");
                        }

                    } else if (collidableFigure instanceof EnemyMissileMelee) {//this is where the enemy melee attacks would go
                        collidableFigure.goNextState();
                        GameData.multiplier = 0;
                        EnemyMissileMelee.dealDamage();

                    } else if (collidableFigure instanceof EnemyMissileWarlock) {
                        collidableFigure.goNextState();
                        GameData.multiplier = 0;
                        EnemyMissileWarlock.dealDamage();

                    } else if (collidableFigure instanceof EnemyMissileSummonPet) {
                        GameData.multiplier = 0;
                        EnemyMissileSummonPet.dealDamage();

                    } else if (collidableFigure instanceof EnemyMissilePoison) {
                        GameData.multiplier = 0;
                        EnemyMissilePoison.dealDamage();
                        EnemyMissilePoison.dealDamage();
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
                if ((friendFigure instanceof Missile || friendFigure instanceof Melee || friendFigure instanceof MyBullet || friendFigure instanceof Shield || friendFigure instanceof ItemExplosion)
                        && friendFigure.getCollisionBox().intersects(collidableFigure.getCollisionBox())
                        && friendFigure.state != friendFigure.STATE_DONE
                        && collidableFigure.state != collidableFigure.STATE_DONE) //Enemy -> SuicideEnemy
                //------------------------------
                {
                    //Enemy -> SuicideEnemy
                    //------------------------------
                    if (collidableFigure instanceof SuicideEnemy) {
                        ((SuicideEnemy) collidableFigure).takeDamage(Shooter.getWeaponPower());
                        if (((SuicideEnemy) collidableFigure).getHealth() <= 0) {
                            collidableFigure.goNextState();
                        }
                        MainWindow.score += 5;
                    } else if (collidableFigure instanceof MeleeEnemy) {
                        //Enemy -> MeleeEnemy
                        //------------------------------
                        ((MeleeEnemy) collidableFigure).takeDamage(Shooter.getWeaponPower());
                        if (((MeleeEnemy) collidableFigure).getHealth() <= 0) {
                            collidableFigure.goNextState();
                        }
                        MainWindow.score += 5;
                    } else if (collidableFigure instanceof SlowMage) {
                        //Enemy -> SlowMage
                        //------------------------------
                        ((SlowMage) collidableFigure).takeDamage(Shooter.getWeaponPower());
                        if (((SlowMage) collidableFigure).getHealth() <= 0) { //if health goes to 0, it dies
                            collidableFigure.goNextState();
                        }
                    } else if (collidableFigure instanceof BlinkMage) {
                        //Enemy -> BlinkMage
                        //------------------------------
                        ((BlinkMage) collidableFigure).takeDamage(Shooter.getWeaponPower());
                        if (((BlinkMage) collidableFigure).getHealth() <= 0) {
                            collidableFigure.goNextState();
                        }
                        MainWindow.score += 5;
                    } else if (collidableFigure instanceof BossSummon) {
                        //Boss -> BossWarlock
                        //------------------------------
                        ((BossSummon) collidableFigure).takeDamage(5);
                        if (((BossSummon) collidableFigure).getHealth() <= 0) {
                            collidableFigure.goNextState();
                        }
                    } else if (collidableFigure instanceof BossSummon) {
                        //Boss -> BossWarlockPet
                        //------------------------------
                        ((BossSummonPet) collidableFigure).takeDamage(5);
                        if (((BossSummonPet) collidableFigure).getHealth() <= 0) {
                            collidableFigure.goNextState();
                        }
                    } else if (collidableFigure instanceof BossSnake) {
                        //Boss -> BossSnake
                        //------------------------------
                        ((BossSnake) collidableFigure).takeDamage(5);
                        if (((BossSnake) collidableFigure).getHealth() <= 0) {
                            collidableFigure.goNextState();
                        }
                    } else {
                        collidableFigure.goNextState();
                    }
                    friendFigure.goNextState();
                    //collidableFigure.goNextState();                        

                }
            });
        });

//        // detect collisions between friendFigure and enemyFigures
//        // if detected, mark it as STATE_DONE, so that
//        // they can be removed at update() method
//        for (GameFigure s : Main.gameData.enemyFigures) {            
//                    
//            /* * * * * * * * * * * * * * * * * * * * * *         
//            *   ____  _                 _              *
//            *  / ___|| |__   ___   ___ | |_ ___ _ __   *
//            *  \___ \| '_ \ / _ \ / _ \| __/ _ \ '__|  *
//            *   ___) | | | | (_) | (_) | ||  __/ |     *
//            *  |____/|_| |_|\___/ \___/ \__\___|_|     *                                       
//            *                                          *
//            * * * * * * * * * * * * * * * * * * * * * */
//            
//            //Shooter Takes Damage
//            //------------------------------
//            if (Main.gameData.shooter.getCollisionBox().intersects(s
//                    .getCollisionBox()) && s.state != s.STATE_DYING) { //if shooter intersects any enemyfigure do this
//                if (s instanceof EnemyMissile) {//this is if a hurtful enemy missile happens
//                    s.goNextState();
//                    GameData.multiplier = 0;
//                    EnemyMissile.dealDamage();
//                    
//                } else if (s instanceof SuicideEnemy) {//do the enemy slow missile stuff here
//                    s.goNextState();
//                    GameData.multiplier = 0;
//                    SuicideEnemy.dealDamage();
//                    
//                } else if (s instanceof EnemyMissileSlow) {//do the enemy slow missile stuff here
//                    s.goNextState();
//                    EnemyMissileSlow.dealDamage();
//                    for (int i = 0; i < 5; i++) {
//                        GameData.shooter.isSprint(FALSE);
//                        System.out.println("Counter = " + i);
//                    }
//                    if (GameData.shooter.isSprint() == FALSE) {
//                        System.out.println("Sprint is off");
//                    }
//                    
//                } else if (s instanceof EnemyMissileMelee) {//this is where the enemy melee attacks would go
//                    s.goNextState();
//                    GameData.multiplier = 0;
//                    EnemyMissileMelee.dealDamage();
//                    
//                } else if (s instanceof EnemyMissileWarlock) {
//                    s.goNextState();
//                    GameData.multiplier =0;
//                    EnemyMissileWarlock.dealDamage();
//                    
//                } else if (s instanceof EnemyMissileSummonPet){
//                    GameData.multiplier =0;
//                    EnemyMissileSummonPet.dealDamage();
//                }
//            }
//            
//           /* * * * * * * * * * * * * * * * * * * * *          
//           *    _____                               * 
//           *   | ____|_ __   ___ _ __ ___  _   _    *
//           *   |  _| | '_ \ / _ \ '_ ` _ \| | | |   *
//           *   | |___| | | |  __/ | | | | | |_| |   *
//           *   |_____|_| |_|\___|_| |_| |_|\__, |   *
//           *                               |___/    *
//            * * * * * * * * * * * * * * * * * * * * */
//            
//            //Enemy Takes Damage
//            //------------------------------
//            for (GameFigure f : Main.gameData.friendFigures) { //only process gamefigure collisionboxes if they are weapon or missile
//                if (f instanceof Missile || f instanceof Melee || f instanceof MyBullet || f instanceof Shield) {
//                    if (f.getCollisionBox().intersects(s.getCollisionBox()) /*&& f.state != f.STATE_DYING && s.state != s.STATE_DYING **/
//                            && f.state != f.STATE_DONE
//                            && s.state != s.STATE_DONE) {
//                        
//                        //Enemy -> SuicideEnemy
//                        //------------------------------
//                        if(s instanceof SuicideEnemy){
//                            ((SuicideEnemy) s).takeDamage(Shooter.getWeaponPower());
//                            if(((SuicideEnemy) s).getHealth() <= 0){
//                                s.goNextState();
//                            }
//                            MainWindow.score += 5;
//                        } else if (s instanceof MeleeEnemy){
//                          //Enemy -> MeleeEnemy
//                          //------------------------------
//                            ((MeleeEnemy) s).takeDamage(Shooter.getWeaponPower());
//                            if(((MeleeEnemy) s).getHealth() <= 0){
//                                s.goNextState();
//                            }
//                            MainWindow.score += 5;
//                        } else if (s instanceof SlowMage){
//                          //Enemy -> SlowMage
//                          //------------------------------
//                            ((SlowMage) s).takeDamage(Shooter.getWeaponPower());
//                            if(((SlowMage) s).getHealth() <= 0){ //if health goes to 0, it dies
//                                s.goNextState();
//                            }
//                        } else if (s instanceof BlinkMage){
//                          //Enemy -> BlinkMage
//                          //------------------------------
//                            ((BlinkMage) s).takeDamage(Shooter.getWeaponPower());
//                            if(((BlinkMage) s).getHealth() <= 0){
//                                s.goNextState();
//                            }
//                            MainWindow.score += 5;
//                        } else if (s instanceof BossSummon) {
//                          //Boss -> BossWarlock
//                          //------------------------------
//                            ((BossSummon) s).takeDamage(5);
//                            if(((BossSummon)s).getHealth() <= 0){
//                                s.goNextState();
//                            }
//                        } else if (s instanceof BossSummon) {
//                          //Boss -> BossWarlockPet
//                          //------------------------------
//                            ((BossSummonPet) s).takeDamage(5);
//                            if(((BossSummonPet)s).getHealth() <= 0){
//                                s.goNextState();
//                            }
//                        } else{
//                            s.goNextState();
//                        }
//                        f.goNextState();
//                        //s.goNextState();                        
//                        MainWindow.scoreText.setText("Score: "
//                                + MainWindow.score + " || Coins: "
//                                + MainWindow.coins);
//                    }
//                }
//            }
//
//            //detection for enemy attacks hitting terrain
//            for (GameFigure t : Main.gameData.terrainFigures) {
//                if (s.getCollisionBox().intersects(t.getCollisionBox())
//                        && !((s instanceof BlinkMage)
//                        || (s instanceof SuicideEnemy)
//                        || (s instanceof MeleeEnemy) || (s instanceof SlowMage))
//                        && (t instanceof BlockTerrain)) {
//                    s.goNextState();
//                }
//            }
//        }
//        //detection for freindly attacks hitting terrain
//        for (GameFigure m : Main.gameData.friendFigures) {
//            for (GameFigure t : Main.gameData.terrainFigures) {
//                if (m.getCollisionBox().intersects(t.getCollisionBox())) {
//                    if (!(m instanceof Shooter) && (t instanceof BlockTerrain)) {
//                        m.goNextState();
//                    }
//                }
//            }
//        }
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
