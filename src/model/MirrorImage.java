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
public class MirrorImage extends GameFigure {

    Image image;
    int counter;
    
    public MirrorImage(float x, float y) {
        super(x, y);
        counter = 150;
        try {
            image = ImageIO.read(getClass().getResource("/resources/WalkSouth.png"));
                    } 
        catch (IOException ex) {
            Logger.getLogger(MirrorImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void render(Graphics2D g) {
        
        g.drawImage(image, (int) super.x, (int) super.y, PLAYER_WIDTH, PLAYER_HEIGHT, null);
        
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {
        if(state == STATE_ALIVE)
        {
            counter--;
        }
        if(counter  < 1)
        {
            this.goNextState();
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, PLAYER_HEIGHT, PLAYER_WIDTH);
    }
    
}
