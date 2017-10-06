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
    private int health;
    private int speed;
    private int strenth;
    private int mana;
    private int maxMana;
    private int maxHealth;
    //made static so shop can access invo
    public static Item[] inventory; 
    
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
        maxHealth = health;
        maxMana = mana;
        inventory = new Item[4];

        // Tests for items and equipment
        //---------------------------------------------------------------------
        inventory[0] = new WeakPotion(1);
        inventory[1] = new MediumPotion(2);
        inventory[2] = new StrongPotion(3);
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
        
        
        g.setColor(Color.red);
        g.fillRect(20, 450, health, 20);
        g.setColor(Color.white);
        g.drawRect(20,450,maxHealth, 20);
        g.setColor(Color.blue);
        g.fillRect(20, 480, mana, 20);
        g.setColor(Color.white);
        g.drawRect(20,480,maxMana, 20);
        g.drawRect(20,420,20,20);
        g.drawRect(50,420,20,20);
        g.drawRect(80,420,20,20);
        g.drawRect(110,420,20,20);
        for(int i=0;i<4;i++)
        {
            if(inventory[i] != null)
            {
                g.drawImage(inventory[i].getIcon(), 20+ i*30 , 420, 20, 20, null);
            }
        }
        
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStrenth() {
        return strenth;
    }

    public void setStrenth(int strenth) {
        this.strenth = strenth;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    
    public void useItem(Consumable item,int pos)
    {
        if(item != null)
        {
        item.consumeItem(this);
        inventory[pos] = null;
        }
    }
}
