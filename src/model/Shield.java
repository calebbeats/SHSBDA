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
public class Shield extends GameFigure {

    private Image shieldImage;
    private Image shieldImage2;
    private int animationCheck = 0;
    private static final int SIZE = 50;
    
    public Shield(float x, float y) {
        super(x, y);
        super.state = STATE_ALIVE;
        
        try {
            shieldImage = ImageIO.read(getClass().getResource("/resources/Shield1.png"));
            shieldImage2 = ImageIO.read(getClass().getResource("/resources/Shield2.png"));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    

    @Override
    public void render(Graphics2D g) {
        if(this.state == STATE_ALIVE)
        {
            if (animationCheck == 0) {
                g.drawImage(shieldImage, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
                animationCheck = 1;
            } else {
                g.drawImage(shieldImage2, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
                animationCheck = 0;
            }
        }
        if(this.state == STATE_DYING)
        {
            
        }
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {
        this.x = GameData.shooter.x-10;
        this.y = GameData.shooter.y-10;
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, SIZE * 0.9D, SIZE * 0.9D);
    }
    
}
