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
public class DefensivePulse extends Consumable {

    public DefensivePulse(int i) {
        
         super(i);
         try{
         icon = ImageIO.read(getClass().getResource("/resources/ShieldIcon.png"));
         }
         catch (IOException ex) {
             JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
         }
     
    }
 
     
 
     @Override
     public void consumeItem(Shooter s) {
         
         gameData.addFriendlyFigure(new Shield(s.x-10,s.y-10));
     }
     
 }
