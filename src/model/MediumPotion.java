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
public class MediumPotion extends Consumable {

    public MediumPotion(int i) {
        super(i);
    }

    @Override
    public void consumeItem(Shooter s) {
        s.health = s.health + 20;
    }
}
