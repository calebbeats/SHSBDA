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
public class PowerDecorator implements WeaponComponent {
    
    protected WeaponComponent weapon;
    
    public PowerDecorator(WeaponComponent weapon) {
        this.weapon = weapon;
    }
    
    @Override
    public void shoot(Shooter shooter, int px, int py) {
        weapon.shoot(shooter, px,  py);
    }
  
}
