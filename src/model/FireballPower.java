/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Main;
import java.awt.Color;

/**
 *
 * @author caleb
 */
public class FireballPower extends PowerDecorator {

    public FireballPower(WeaponComponent weapon) {
        super(weapon);
    }
    
    @Override
    public void shoot(Shooter shooter, int px, int py) {
         super.shoot(shooter, px, py); 
         addedPower(shooter, px, py);
    }
    
    public void addedPower(Shooter shooter, int px, int py) {
        Missile m = new Missile(
                shooter.getXofMissileShoot()-10,
                shooter.getYofMissileShoot(),
                px-10, py // target location where the missile explodes
                );
        synchronized (Main.gameData.friendFigures) {
            Main.gameData.friendFigures.add(m);   
        }
        Missile n = new Missile(
                shooter.getXofMissileShoot()+10,
                shooter.getYofMissileShoot(),
                px+10, py // target location where the missile explodes
                );
        synchronized (Main.gameData.friendFigures) {
            Main.gameData.friendFigures.add(n);   
        }
    }
}
    


