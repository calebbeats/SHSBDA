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
public abstract class Item {
    public int id;
    public enumtype type;
    public enum enumtype { CONSUMABLE, EQUIPMENT }
    
    public Item(int i)
    {
        id = i;
    }
}

