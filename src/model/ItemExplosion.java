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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import static model.Shooter.PLAYER_HEIGHT;
import static model.Shooter.PLAYER_WIDTH;

/**
 *
 * @author caleb
 */
public class ItemExplosion extends GameFigure{

    Image image1;
    Image image2;
    Image image3;
    int counter;
    
    public ItemExplosion(float x, float y) {
        super(x, y);
        counter = 60;
        try {
            image1 = ImageIO.read(getClass().getResource("/resources/ItemExplosion1.png"));
            image2 = ImageIO.read(getClass().getResource("/resources/ItemExplosion2.png"));
            image3 = ImageIO.read(getClass().getResource("/resources/ItemExplosion3.png"));
                    } 
        catch (IOException ex) {
            Logger.getLogger(MirrorImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(state == STATE_ALIVE)
        {
            if(counter >40)
            {
                 g.drawImage(image1, (int) super.x-10, (int) super.y-10, 50, 50, null);
            }
            else if(counter > 20)
            {
                g.drawImage(image2, (int) super.x-10, (int) super.y-10, 50, 50, null);
            }
            else
            {
                g.drawImage(image3, (int) super.x-10, (int) super.y-10, 50, 50, null);
            }
        }
        else if(state == STATE_DYING)
        {
            if(counter >40)
            {
                 g.drawImage(image1, (int) super.x-10, (int) super.y-10, 50, 50, null);
            }
            else if(counter > 20)
            {
                g.drawImage(image2, (int) super.x-10, (int) super.y-10, 50, 50, null);
            }
            else
            {
                g.drawImage(image3, (int) super.x-10, (int) super.y-10, 50, 50, null);
            }
        }
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {
        if(counter < 1)
        {
            this.goNextState();
        }
        if(state == STATE_DYING)
        {
            if(counter < 1)
            {
                this.goNextState();
            }
            else counter--;
        }
        else counter--;
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x-10, this.y -10, 50, 50);
    }
    
}
