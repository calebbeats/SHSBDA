package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;

public class Shooter extends GameFigure {

    private Image launcherImage;
    private Image shooterLeft;
    private Image shooterRight;
    public WeaponComponent weapon;
    int deadTimer= 0;
    
    public Shooter(int x, int y) {
        super(x, y);
        super.state = STATE_ALIVE;
        weapon = new BasicWeapon();
        
        
        launcherImage = null;
        
        try {
            launcherImage = ImageIO.read(getClass().getResource("shooterRight.png"));
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
        if(state == STATE_DYING)
        {
            if(deadTimer < 10)
            {
                deadTimer++;
            }
            else
            {
                this.goNextState();
            }
        }
    }
    
    public void moveLeft()
    {
        try {
            launcherImage = ImageIO.read(getClass().getResource("shooterLeft.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }
    
    public void moveRight()
    {
        try {
            launcherImage = ImageIO.read(getClass().getResource("shooterRight.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    public void translate(int dx, int dy) {
        super.x += dx;
        super.y += dy;
    }
    
    // Missile shoot location: adjut x and y to the image
    public float getXofMissileShoot() {
        return super.x;
    }
    
    public float getYofMissileShoot() {
        return super.y;
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, 40, 40);
    }

    @Override
    public void shoot() {
        System.out.println("Shooter Shoots");
    }

}
