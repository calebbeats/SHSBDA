/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.Shooter;

/**
 *
 * @author caleb
 */
public interface WeaponComponent {
    void shoot(Shooter shooter, int x, int y); 
}

