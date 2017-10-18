/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author caleb
 */
public class PowerUp extends GameFigure{

    public Image powerUpImage;
    
    public PowerUp(float x, float y) {
        super(x, y);
        try {
            powerUpImage = ImageIO.read(getClass().getResource("/resources/PowerUp.png"));
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(state == STATE_ALIVE)
            {
                g.drawImage(powerUpImage, (int)super.x, (int)super.y, 
                30, 30, null);
            }
    }

    @Override
    public void shoot() {
        
    }

    @Override
    public void update() {
        if(state == STATE_DYING)
        {
            this.goNextState();
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, 20, 40);
    }
    
}
