/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Main;

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
                px, py // target location where the missile explodes
                );
        synchronized (Main.gameData.friendFigures) {
            Main.gameData.friendFigures.add(m);   
        }
    }
}

