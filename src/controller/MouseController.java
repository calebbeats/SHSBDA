package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.GameData;
import model.MediumPotion;
import model.Melee;
import model.Missile;
import model.Shooter;
import model.StrongPotion;
import model.WeakPotion;
import view.MainWindow;

public class MouseController extends MouseAdapter {

    private int px;
    private int py;
    private Audio a;
    private int index;
    private static boolean weapon1Bought = false, weapon2Bought = false, weapon3Bought = false;

    public MouseController() {
        a = new Audio();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        px = me.getX();
        py = me.getY();
        Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);

        switch (Main.gameState) {
            case Start:
            case Pause:
            case Winner:
                if (!Main.gameState.equals(Main.GameState.Pause)
                        && px > Main.gamePanel.startGameButton.x
                        && px < Main.gamePanel.startGameButton.getMaxX()
                        && py > Main.gamePanel.startGameButton.y
                        && py < Main.gamePanel.startGameButton.getMaxY()) {
                    if (a.getC() != null) {
                        a.getC().stop();
                    }
                    a.playAudio("/resources/chugchug.wav");                    
                    Main.gameState = Main.GameState.Run;
                } else if (px > Main.gamePanel.quitGameButton.x
                        && px < Main.gamePanel.quitGameButton.getMaxX()
                        && py > Main.gamePanel.quitGameButton.y
                        && py < Main.gamePanel.quitGameButton.getMaxY()) {
                    System.exit(0);
                } else if (px > Main.gamePanel.highScoreButton.x
                        && px < Main.gamePanel.highScoreButton.getMaxX()
                        && py > Main.gamePanel.highScoreButton.y
                        && py < Main.gamePanel.highScoreButton.getMaxY()) {
                    Main.gameState = Main.GameState.HighScore;
                }
                break;
            case GameOver:
                if (!Main.gameState.equals(Main.GameState.Pause)
                        && px > Main.gamePanel.startGameButton.x
                        && px < Main.gamePanel.startGameButton.getMaxX()
                        && py > Main.gamePanel.startGameButton.y
                        && py < Main.gamePanel.startGameButton.getMaxY()) {
                    if (a.getC() != null) {
                        a.getC().stop();
                    }
                    a.playAudio("/resources/chugchug.wav");
                    Main.gameState = Main.GameState.Run;
                } else if (px > Main.gamePanel.quitGameButton.x
                        && px < Main.gamePanel.quitGameButton.getMaxX()
                        && py > Main.gamePanel.quitGameButton.y
                        && py < Main.gamePanel.quitGameButton.getMaxY()) {
                    System.exit(0);
                } else if (px > Main.gamePanel.highScoreButton.x
                        && px < Main.gamePanel.highScoreButton.getMaxX()
                        && py > Main.gamePanel.highScoreButton.y
                        && py < Main.gamePanel.highScoreButton.getMaxY()) {
                    Main.gameState = Main.GameState.HighScore;
                }
                break;
            case HighScore:
                if (px > Main.gamePanel.backButton.x
                        && px < Main.gamePanel.backButton.getMaxX()
                        && py > Main.gamePanel.backButton.y
                        && py < Main.gamePanel.backButton.getMaxY()) {
                    Main.gameState = Main.isPause ? Main.GameState.Pause
                            : Main.GameState.Start;
                }
                break;
            case Run:
                if (me.getButton() == MouseEvent.BUTTON1 && !shooter.isSprint()) {//Left click detected, initiate melee attack
                    //Audio For Sword Swing
                    //------------------------------
                    a.playAudio("/resources/swoosh.wav");
                    //melee attack in the direction the mouse is in regards to the player
                    Melee m = new Melee(
                            shooter.getXofMissileShoot(),
                            shooter.getYofMissileShoot(),
                            px, py // direction in which to melee attack
                    );
                    Main.gameData.friendFigures.add(m);
                }
                if (me.getButton() == MouseEvent.BUTTON3 && !shooter.isSprint() && shooter.getMana() > 9) {
                    if (KeyController.chooseMissile == false) {
                        //Right click detected, initiate ranged attack
                        //shoot a missle at the mouse press location
                        a.playAudio("/resources/shooterFBwoosh.wav");
                        Missile m = new Missile(
                                shooter.getXofMissileShoot(),
                                shooter.getYofMissileShoot(),
                                px, py // target location where the missile explodes
                        );
                        Main.gameData.friendFigures.add(m);
                    }
                }
                break;
            case LevelComplete:
//                if (a.getC() != null) {
//                    a.getC().stop();
//                }
                if (px > Main.gamePanel.shopGameButton.x
                        && px < Main.gamePanel.shopGameButton.getMaxX()
                        && py > Main.gamePanel.shopGameButton.y
                        && py < Main.gamePanel.shopGameButton.getMaxY()) {
                    Main.gameState = Main.GameState.Shop;
                } else if (px > Main.gamePanel.continueLevelButton.x
                        && px < Main.gamePanel.continueLevelButton.getMaxX()
                        && py > Main.gamePanel.continueLevelButton.y
                        && py < Main.gamePanel.continueLevelButton.getMaxY()) {
                    if (Main.gameLevel < 12) {
                        Main.gameLevel++;
                        Main.gameData = new GameData();
                        Main.animator = new Animator();
                        Main.gameState = Main.GameState.Run;
                    } else {
                        Main.gameState = Main.GameState.Start;
                    }
                }

                break;
            case Shop:
                if (px > Main.gamePanel.weakPotionButton.x
                        && px < Main.gamePanel.weakPotionButton.getMaxX()
                        && py > Main.gamePanel.weakPotionButton.y
                        && py < Main.gamePanel.weakPotionButton.getMaxY()
                        && inventoryCapacityCheck() && MainWindow.coins >= 1) {
                    MainWindow.coins -= 1;
                    Shooter.inventory[index] = new WeakPotion(1);
                } else if (px > Main.gamePanel.mediumPotionButton.x
                        && px < Main.gamePanel.mediumPotionButton.getMaxX()
                        && py > Main.gamePanel.mediumPotionButton.y
                        && py < Main.gamePanel.mediumPotionButton.getMaxY()
                        && inventoryCapacityCheck() && MainWindow.coins >= 2) {
                    MainWindow.coins -= 2;
                    Shooter.inventory[index] = new MediumPotion(1);
                } else if (px > Main.gamePanel.strongPotionButton.x
                        && px < Main.gamePanel.strongPotionButton.getMaxX()
                        && py > Main.gamePanel.strongPotionButton.y
                        && py < Main.gamePanel.strongPotionButton.getMaxY()
                        && inventoryCapacityCheck() && MainWindow.coins >= 3) {
                    MainWindow.coins -= 3;
                    Shooter.inventory[index] = new StrongPotion(1);
                } else if (px > Main.gamePanel.weaponUpgrade1.x
                        && px < Main.gamePanel.weaponUpgrade1.getMaxX()
                        && py > Main.gamePanel.weaponUpgrade1.y
                        && py < Main.gamePanel.weaponUpgrade1.getMaxY()
                        && MainWindow.coins >= 1
                        && weapon1Bought == false) {
                    weapon1Bought = true;
                    MainWindow.coins -= 1;
                    shooter.setWeaponPower(2);
                } else if (px > Main.gamePanel.weaponUpgrade2.x
                        && px < Main.gamePanel.weaponUpgrade2.getMaxX()
                        && py > Main.gamePanel.weaponUpgrade2.y
                        && py < Main.gamePanel.weaponUpgrade2.getMaxY()
                        && MainWindow.coins >= 1
                        && weapon2Bought == false) {
                    weapon2Bought = true;
                    MainWindow.coins -= 1;
                    shooter.setWeaponPower(3);
                } else if (px > Main.gamePanel.weaponUpgrade3.x
                        && px < Main.gamePanel.weaponUpgrade3.getMaxX()
                        && py > Main.gamePanel.weaponUpgrade3.y
                        && py < Main.gamePanel.weaponUpgrade3.getMaxY()
                        && MainWindow.coins >= 5
                        && weapon3Bought == false) {
                    weapon3Bought = true;
                    MainWindow.coins -= 5;
                    shooter.setWeaponPower(4);
                } else if (px > Main.gamePanel.backButton.x
                        && px < Main.gamePanel.backButton.getMaxX()
                        && py > Main.gamePanel.backButton.y
                        && py < Main.gamePanel.backButton.getMaxY()) {
                    Main.gameState = Main.GameState.LevelComplete;
                }
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ((Shooter) Main.gameData.friendFigures.get(0)).setMouseMovedEvent(e);
    }

    public static boolean getWep1 (){
        return MouseController.weapon1Bought;
    }
    
    public static boolean getWep2 (){
        return MouseController.weapon2Bought;
    }
    
    public static boolean getWep3 (){
        return MouseController.weapon3Bought;
    }
    
    private boolean inventoryCapacityCheck() {
        for (int i = 0; i < Shooter.inventory.length; i++) {
            if (Shooter.inventory[i] == null) {
                index = i;
                return true;
            }
        }
        return false;
    }
}
