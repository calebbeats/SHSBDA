/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
//import static model.FlyEnemy.getImage;
import static model.GameFigure.STATE_ALIVE;
import view.GamePanel;
/**
 *
 * @author dinhn
 */
public interface FlyBoss {
    
    Rectangle2D.Double getCollisionBox();
    
    boolean Collides(GameFigure anotherGF);
    
    String getGFType();
    
    void update();
    
    void shoot();
    
    void takeDamage(int i);
    
    int getHealth();
    
    void setHealth(int health);
    
    int getMaxHealth();
    
    void setMaxHealth(int maxHealth);
}
