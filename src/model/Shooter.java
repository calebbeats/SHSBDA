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

    //Images for animations go below
    //----------------------------------
    private Image launcherImage;
    private Image shooterLeft;
    private Image shooterRight;
    public WeaponComponent weapon;
    int deadTimer= 0;
    // ----------------------------------
    
    //Player Stats
    //-------------------
    public int health;
    public int speed;
    public int strenth;
    public int mana;
    //-----------------
    
    //test object
    //-------------------
    public WeakPotion p1;
    public MediumPotion p2;
    public StrongPotion p3;
    //-------------------
    
    public Shooter(int x, int y) {
        super(x, y);
        super.state = STATE_ALIVE;
        weapon = new BasicWeapon();
        health = 100;
        
        System.out.print("Testing WeakPotion" + "\n");
        p1 = new WeakPotion(1);
        System.out.print("Health before : " + health + "\n");
        p1.consumeItem(this);
        System.out.print("Health after : " + health + "\n");
        
        System.out.print("Testing MediumPotion" + "\n");
        p2 = new MediumPotion(2);
        System.out.print("Health before : " + health + "\n");
        p2.consumeItem(this);
        System.out.print("Health after : " + health + "\n");
        
        System.out.print("Testing StrongPotion" + "\n");
        p3 = new StrongPotion(3);
        System.out.print("Health before : " + health + "\n");
        p3.consumeItem(this);
        System.out.print("Health after : " + health + "\n");
        
        
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
        return new Rectangle2D.Double(this.x, this.y, 30, 30);
    }

    @Override
    public void shoot() {
        System.out.println("Shooter Shoots");
    }

    //Temporary method to test healing items.
    public void takeDamage(int i)
    {
        health = health - i;
    }
    
}
