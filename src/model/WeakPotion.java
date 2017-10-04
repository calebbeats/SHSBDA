/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;



/**
 *
 * @author caleb
 */
public class WeakPotion extends Consumable {
    
    public WeakPotion(int i) {
        super(i);
        try{
        icon = ImageIO.read(getClass().getResource("SmallPotion.png"));
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
    }

    @Override
    public void consumeItem(Shooter s) {
        if(s.getHealth() + 10 > s.getMaxHealth())
        {
            s.setHealth(s.getMaxHealth());
        }
        else
        {
             s.setHealth(s.getHealth() + 10);
        }
    }
    
}
