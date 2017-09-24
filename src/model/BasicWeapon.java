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
public class BasicWeapon implements WeaponComponent {

    @Override
    public void shoot(Shooter shooter, int px, int py)
    {
        Missile m = new Missile(
                shooter.getXofMissileShoot(),
                shooter.getYofMissileShoot(),
                px, py, Color.RED // target location where the missile explodes
                );
        synchronized (Main.gameData.friendFigures) {
            Main.gameData.friendFigures.add(m);   
        }
    }
    
    @Override
    public void melee(Shooter shooter, int px, int py) {
        Melee m = new Melee(
                shooter.getXofMissileShoot(),
                shooter.getYofMissileShoot(),
                px, py // direction in which to melee attack
            );
        synchronized (Main.gameData.friendFigures) {
            Main.gameData.friendFigures.add(m);
    }
}
}

