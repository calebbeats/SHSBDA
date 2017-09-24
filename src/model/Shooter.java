package model;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;

public class Shooter extends GameFigure {

    private static final int PLAYER_WIDTH = 30, PLAYER_HEIGHT = 30;

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

    //Images for animations go below
    //----------------------------------
    private Image launcherImage;
    //private Image shooterLeft;
    //private Image shooterRight;
    public WeaponComponent weapon;
    int deadTimer = 0;
    // ----------------------------------

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
                PLAYER_WIDTH, PLAYER_HEIGHT, null);
    }

    @Override
    public void update() {

        if (state == STATE_DYING) {
            if (deadTimer < 10) {
                deadTimer++;
            } else {
                this.goNextState();
            }
        } else {
            //for now this is how the character moves left and right and back
            //can remove this once we get the mouse direction working
            if (velocityX < 0) {
                this.moveLeft();
            } else if (velocityX > 0 || velocityX == 0 && velocityY > 0) {
                this.moveRight();
            } else if (velocityX == 0 && velocityY < 0) {
                this.moveBack();
            }

            moveX();
            moveY();

            // Window boundary
            if (super.x <= 0) {
                super.x = 0;
            }
            if (super.x >= Main.WIN_WIDTH - PLAYER_WIDTH) {
                super.x = Main.WIN_WIDTH - PLAYER_WIDTH;
            }
            if (super.y <= 0) {
                super.y = 0;
            }
            if (super.y >= Main.WIN_HEIGHT - PLAYER_HEIGHT - 70) {
                super.y = Main.WIN_HEIGHT - PLAYER_HEIGHT - 70;
            }
        }
    }

    private void moveX() {

        Main.gameData.terrainFigures.forEach(terrain -> {
            if (velocityX > 0) {
                Shooter shooterIntendedPossition = new Shooter((int) super.x + velocityX, (int) super.y);

                if (!shooterIntendedPossition.getCollisionBox().intersects(terrain.getCollisionBox())) {
                    super.x += velocityX;
                }
            } else if (velocityX < 0) {
                Shooter shooterIntendedPossition = new Shooter((int) super.x + velocityX, (int) super.y);

                if (!shooterIntendedPossition.getCollisionBox().intersects(terrain.getCollisionBox())) {
                    super.x += velocityX;
                }
            }
        });
    }

    private void moveY() {

        Main.gameData.terrainFigures.forEach(terrain -> {
            if (velocityY > 0) {
                Shooter shooterIntendedPossition = new Shooter((int) super.x, (int) super.y + velocityY);

                if (!shooterIntendedPossition.getCollisionBox().intersects(terrain.getCollisionBox())) {
                    super.y += velocityY;
                }
            } else if (velocityY < 0) {
                Shooter shooterIntendedPossition = new Shooter((int) super.x, (int) super.y + velocityY);

                if (!shooterIntendedPossition.getCollisionBox().intersects(terrain.getCollisionBox())) {
                    super.y += velocityY;
                }
            }
        });
    }

    private void moveLeft() {
        try {
            launcherImage = ImageIO.read(getClass().getResource("shooterLeft1.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    private void moveRight() {
        try {
            launcherImage = ImageIO.read(getClass().getResource("shooterRight1.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    private void moveBack() {
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
        return new Rectangle2D.Double(this.x, this.y, PLAYER_HEIGHT, PLAYER_WIDTH);
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
