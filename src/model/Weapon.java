/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author caleb
 */
public abstract class Weapon extends Item{
    
    public Weapon(int i) {
        super(i);
    }
    
    public abstract void equipWeapon(Shooter s);

    
}
