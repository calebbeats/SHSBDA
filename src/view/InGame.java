/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import model.BlinkMage;
import model.EnemyMissile;
import model.FireMage;
import static model.GameData.shooter;
import model.GameFigure;
import model.PowerUp;
import model.RockMage;
import model.Shooter;
import model.SuicideEnemy;
import static view.GamePanel.height;
import static view.GamePanel.width;

public class InGame implements GameViewStrategy{

    public InGame()
    {
        Main.gameData.enemyFigures.removeAll(Main.gameData.enemyFigures);
        Main.gameData.friendFigures.removeAll(Main.gameData.friendFigures);
        shooter = new Shooter(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT - 130);
        Main.gameData.friendFigures.add(shooter);
        Main.gameData.enemyFigures.add(new PowerUp(430,470));
        Main.gameData.enemyFigures.add(new FireMage(50, 60));
        Main.gameData.enemyFigures.add(new FireMage(400, 20));
        Main.gameData.enemyFigures.add(new RockMage(50, 20));
        Main.gameData.enemyFigures.add(new RockMage(300, 20));
        Main.gameData.enemyFigures.add(new BlinkMage(300,300));
        Main.gameData.enemyFigures.add(new EnemyMissile(50,50));
        Main.gameData.enemyFigures.add(new SuicideEnemy(50,70));
    }
    
    
    
    @Override
    public void renderGame(Graphics2D g2) {
                       
        g2.clearRect(0, 0, width, height);
       
        g2.setBackground(Color.WHITE);

        if (Main.animator.running) {

            
            synchronized (Main.gameData.enemyFigures) {
                for (GameFigure f : Main.gameData.enemyFigures) {
                    f.render(g2);
                }  
            }
            
            synchronized (Main.gameData.friendFigures) {
                for (GameFigure f : Main.gameData.friendFigures) {
                    f.render(g2);
                }   
            } 
        }
    }
}
