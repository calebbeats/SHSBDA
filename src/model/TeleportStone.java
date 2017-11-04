/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author caleb
 */
public class TeleportStone extends Consumable{

    public TeleportStone(int i) {
        super(i);
        try{
        icon = ImageIO.read(getClass().getResource("/resources/TeleportStone.png"));
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
    }

    @Override
    public void consumeItem(Shooter s) {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        s.x = (float)  b.getX()-115;
        s.y = (float)  b.getY()-55;
    }
    
}
