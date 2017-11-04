/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static controller.Main.gameData;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author caleb
 */
public class FireNova extends Consumable {

    public FireNova(int i) {
        super(i);
        try{
        icon = ImageIO.read(getClass().getResource("/resources/FireNovaIcon.png"));
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
    }

   

    
    @Override
    public void consumeItem(Shooter s) {
        gameData.addFriendlyFigure(new Missile(s.getXofMissileShoot(),s.getYofMissileShoot(),s.getXofMissileShoot()+100,s.getYofMissileShoot()-1));
        gameData.addFriendlyFigure(new Missile(s.getXofMissileShoot(),s.getYofMissileShoot(),s.getXofMissileShoot()-100,s.getYofMissileShoot()-1));
        gameData.addFriendlyFigure(new Missile(s.getXofMissileShoot(),s.getYofMissileShoot(),s.getXofMissileShoot()-1,s.getYofMissileShoot()+100));
        gameData.addFriendlyFigure(new Missile(s.getXofMissileShoot(),s.getYofMissileShoot(),s.getXofMissileShoot()-1,s.getYofMissileShoot()-100));
        
        gameData.addFriendlyFigure(new Missile(s.getXofMissileShoot(),s.getYofMissileShoot(),s.getXofMissileShoot()+60,s.getYofMissileShoot()-60));
        gameData.addFriendlyFigure(new Missile(s.getXofMissileShoot(),s.getYofMissileShoot(),s.getXofMissileShoot()-60,s.getYofMissileShoot()-60));
        gameData.addFriendlyFigure(new Missile(s.getXofMissileShoot(),s.getYofMissileShoot(),s.getXofMissileShoot()+60,s.getYofMissileShoot()+60));
        gameData.addFriendlyFigure(new Missile(s.getXofMissileShoot(),s.getYofMissileShoot(),s.getXofMissileShoot()-60,s.getYofMissileShoot()+60));
        
    }
    
 
}
