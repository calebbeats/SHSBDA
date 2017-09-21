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
public abstract class Consumable extends Item {
    
    public Consumable(int i) {
        super(i);
        super.type = enumtype.CONSUMABLE;
    }
    
    public abstract void consumeItem(Shooter s);
    
}
