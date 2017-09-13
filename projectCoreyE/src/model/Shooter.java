package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Shooter extends GameFigure {

    private Image launcherImage;

    public Shooter(int x, int y) {
        super(x, y);
        super.state = STATE_ALIVE;
        
        launcherImage = null;
        
        try {
            launcherImage = ImageIO.read(getClass().getResource("shooter.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(launcherImage, (int)super.x, (int)super.y, 
                30, 30, null);
    }

    @Override
    public void update() {
        // no periodic update is required (not animated)
        // update is done via 'translate' when a key is pressed
    }

    public void translate(int dx, int dy) {
        super.x += dx;
        super.y += dy;
    }
    
    // Missile shoot location: adjut x and y to the image
    public float getXofMissileShoot() {
        return super.x+15;
    }
    
    public float getYofMissileShoot() {
        return super.y;
    }

    @Override
    public Rectangle2D.Double getCollisionBox()
    {
        //get colissionbox of square shooter (starting x, starting y, length, width)
        return new Rectangle2D.Double(x, y, 30 ,30);
    }

    @Override
    public int getHitPoints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addHitPoints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void goNextState()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}
