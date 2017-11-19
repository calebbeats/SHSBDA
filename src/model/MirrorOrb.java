/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static controller.Main.gameData;

/**
 *
 * @author caleb
 */
public class MirrorOrb extends Consumable {

    public MirrorOrb(int i) {
        super(i);
    }

    @Override
    public void consumeItem(Shooter s) {
        gameData.addFriendlyFigure(new MirrorImage(s.getX()+50, s.getY()));
        gameData.addFriendlyFigure(new MirrorImage(s.getX()-50, s.getY()));
    }
    
}
