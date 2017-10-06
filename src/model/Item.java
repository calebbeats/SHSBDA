/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Image;

/**
 *
 * @author caleb
 */
public abstract class Item {
    public static int id;
    public enumtype type;
    public Image icon;
    public enum enumtype { CONSUMABLE, EQUIPMENT }
    
    public Item(int i)
    {
        id = i;
    }

    public Image getIcon() {
        return icon;
    }
    
}

