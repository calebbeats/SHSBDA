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
public class GemOfMana extends Equipment {
    
    public GemOfMana(int i)
    {
        super(i);
    }
    
    @Override
    public void attachAugment(Shooter s)
    {
        s.mana = s.mana + 20;
    }
    
    @Override
    public void removeAugment(Shooter s)
    {
        s.mana = s.mana - 20;
    }   
}
