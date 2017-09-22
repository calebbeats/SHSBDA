package model;

import controller.Main;
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
    //private Image shooterLeft;
    //private Image shooterRight;
    public WeaponComponent weapon;
    int deadTimer = 0;
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
    public GemOfMana e1;
    //-------------------

    //Player Movement
    //-------------------
    private int velocityX, velocityY;
    //-----------------

    public Shooter(int x, int y) {
        super(x, y);
        super.state = STATE_ALIVE;
        weapon = new BasicWeapon();
        health = 100;
        mana = 100;

        // Tests for items and equipment
        //---------------------------------------------------------------------
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

        System.out.print("Testing GemOfMana" + "\n");
        e1 = new GemOfMana(4);
        System.out.print("Mana before : " + mana + "\n");
        e1.attachAugment(this);
        System.out.print("Mana after : " + mana + "\n");
        e1.removeAugment(this);
        System.out.print("Mana after removal : " + mana + "\n");
        //---------------------------------------------------------------------
        launcherImage = null;

        try {
            launcherImage = ImageIO.read(getClass().getResource("shooterRight1.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(launcherImage, (int) super.x, (int) super.y,
                30, 30, null);
    }

    @Override
    public void update() {

        // used to see if shooter will colide with terrain or will hit window boundary before moving
        Shooter shooterIntededPosition = new Shooter((int) this.x + velocityX, (int) this.y + velocityY);

        Main.gameData.terrainFigures.forEach(terrain -> {
            if (shooterIntededPosition.getCollisionBox().intersects(terrain.getCollisionBox())) {
                if (velocityX < 0 || velocityX > 0) {
                    velocityX = 0;
                } else {
                    velocityY = 0;
                }
            } else if (this.x + velocityX < 0 || this.x + velocityX > Main.WIN_WIDTH - 30) {
                velocityX = 0;
            } else if (this.y + velocityY < 0 || this.y + velocityY > Main.WIN_HEIGHT - 100) {
                velocityY = 0;
            }
        });

        this.x += velocityX;
        this.y += velocityY;

        //for now this is how the character moves left and right and back
        //can remove this once we get the mouse direction working
        if (velocityX < 0) {
            this.moveLeft();
        } else if (velocityX > 0 || velocityX == 0 && velocityY > 0) {
            this.moveRight();
        } else if (velocityX == 0 && velocityY < 0) {
            this.moveBack();
        }

        if (state == STATE_DYING) {
            if (deadTimer < 10) {
                deadTimer++;
            } else {
                this.goNextState();
            }
        }
    }

    public void moveLeft() {
        try {
            launcherImage = ImageIO.read(getClass().getResource("shooterLeft1.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    public void moveRight() {
        try {
            launcherImage = ImageIO.read(getClass().getResource("shooterRight1.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    public void moveBack() {
        try {
            launcherImage = ImageIO.read(getClass().getResource("shooterBack.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
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
    public void takeDamage(int i) {
        health = health - i;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
}
