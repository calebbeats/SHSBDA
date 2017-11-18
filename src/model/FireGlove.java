/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static controller.Main.gameData;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

/**
 *
 * @author caleb
 */
public class FireGlove extends Consumable {

    public FireGlove(int i) {
        super(i);
    }

    @Override
    public void consumeItem(Shooter s) {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        gameData.addFriendlyFigure(new ItemExplosion((float)  b.getX()-115,(float)  b.getY()-55));
       
    }
    
}
