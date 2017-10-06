package model;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;

public class Shooter extends GameFigure {

    public static final int PLAYER_WIDTH = 30, PLAYER_HEIGHT = 30;

    //Player Stats
    //-------------------
    private int health;
    private int speed;
    private int strenth;
    private int mana;
    private int maxMana;
    private int maxHealth;

    //made static so shop can access invo
    public static Item[] inventory = new Item[4]; 
    


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
    private Image[] playerImage;
    //private Image shooterLeft;
    //private Image shooterRight;
    public WeaponComponent weapon;
    int deadTimer = 0;
    // ----------------------------------

    //Player Movement
    //-------------------
    private int velocityX, velocityY, velocitySprint;
    private boolean isSprint;
    private MouseEvent mouseMovedEvent;
    private float angleOfView;
    //-----------------

    public Shooter(int x, int y) {
        super(x, y);
        super.state = STATE_ALIVE;
        weapon = new BasicWeapon();
        health = 100;
        mana = 100;
        maxHealth = health;
        maxMana = mana;
//        inventory = new Item[4];

        // Tests for items and equipment
        // This gets added every time the shooter is created, so it slows game way down
        //---------------------------------------------------------------------
//        inventory[0] = new WeakPotion(1);
//        inventory[1] = new MediumPotion(2);
//        inventory[2] = new StrongPotion(3);
        //---------------------------------------------------------------------

        playerImage = new Image[4];
        try {
            playerImage[0] = ImageIO.read(getClass().getResource("playerRight.png"));
            playerImage[1] = ImageIO.read(getClass().getResource("playerTop.png"));
            playerImage[2] = ImageIO.read(getClass().getResource("playerLeft.png"));
            playerImage[3] = ImageIO.read(getClass().getResource("playerRight.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open Player Image");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        // Update player's view based on angleOfView
        if (angleOfView <= 45 || angleOfView >= 315) {
            g.drawImage(playerImage[0], (int) super.x, (int) super.y,
                    PLAYER_WIDTH, PLAYER_HEIGHT, null);
        } else if (angleOfView > 45 && angleOfView < 135) {
            g.drawImage(playerImage[1], (int) super.x, (int) super.y,
                    PLAYER_WIDTH, PLAYER_HEIGHT, null);
        } else if (angleOfView >= 135 && angleOfView <= 225) {
            g.drawImage(playerImage[2], (int) super.x, (int) super.y,
                    PLAYER_WIDTH, PLAYER_HEIGHT, null);
        } else if (angleOfView > 225 && angleOfView < 315) {
            g.drawImage(playerImage[3], (int) super.x, (int) super.y,
                    PLAYER_WIDTH, PLAYER_HEIGHT, null);
        }

        g.setColor(Color.red);
        g.fillRect(20, 450, health, 20);
        g.setColor(Color.white);
        g.drawRect(20, 450, maxHealth, 20);
        g.setColor(Color.blue);
        g.fillRect(20, 480, mana, 20);
        g.setColor(Color.white);
        g.drawRect(20, 480, maxMana, 20);
        g.drawRect(20, 420, 20, 20);
        g.drawRect(50, 420, 20, 20);
        g.drawRect(80, 420, 20, 20);
        g.drawRect(110, 420, 20, 20);
        for (int i = 0; i < 4; i++) {
            if (inventory[i] != null) {
                g.drawImage(inventory[i].getIcon(), 20 + i * 30, 420, 20, 20, null);
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
            moveX();
            moveY();

            calculateAngleOfView();

            double deltaDistance = calculateDistance(new Point.Float(super.x, super.y))
                    - calculateDistance(new Point.Float(super.x + velocityX, super.y + velocityY));

            if (isSprint && deltaDistance > 0) {
                velocitySprint = 2;
            } else if (isSprint && deltaDistance < 0 || !isSprint) {
                velocitySprint = 0;
            }

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
            if (super.y >= Main.WIN_HEIGHT - PLAYER_HEIGHT - 90) {
                super.y = Main.WIN_HEIGHT - PLAYER_HEIGHT - 90;
            }
        }
    }

    private void calculateAngleOfView() {
        // Find the angle between player's position and mouse's position
        if (mouseMovedEvent != null) {
            angleOfView = (float) Math.toDegrees(
                    Math.atan2(-(mouseMovedEvent.getY() - (super.y + PLAYER_HEIGHT / 2)),
                            mouseMovedEvent.getX() - (super.x + PLAYER_HEIGHT / 2)));

            if (angleOfView < 0) {
                angleOfView += 360;
            }
        }
    }

    private double calculateDistance(Point.Float point) {
        // Find the distance between player's position and mouse's position
        if (mouseMovedEvent != null) {
            return Math.hypot(mouseMovedEvent.getX()
                    - point.x, mouseMovedEvent.getY() - point.y);
        }
        return 0;
    }

    private void moveX() {
        // Allow player to move around terrain and disable sprint if move backward from mouse's position
        Shooter shooterIntendedPossition = new Shooter((int) super.x + velocityX
                + Integer.signum(velocityX) * velocitySprint, (int) super.y);
        Main.gameData.terrainFigures.forEach(terrain -> {
            if (velocityX != 0 && !shooterIntendedPossition
                    .getCollisionBox().intersects(terrain.getCollisionBox())) {
                super.x += velocityX + Integer.signum(velocityX) * velocitySprint;
            }
        });
    }

    private void moveY() {
        // Allow player to move around terrain and disable sprint if move backward from mouse's position
        Shooter shooterIntendedPossition = new Shooter((int) super.x,
                (int) super.y + velocityY + Integer.signum(velocityY) * velocitySprint);
        Main.gameData.terrainFigures.forEach(terrain -> {
            if (velocityY != 0 && !shooterIntendedPossition
                    .getCollisionBox().intersects(terrain.getCollisionBox())) {
                super.y += velocityY + Integer.signum(velocityY) * velocitySprint;
            }
        });
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

    public boolean isSprint() {
        return isSprint;
    }

    public void isSprint(boolean isSprint) {
        this.isSprint = isSprint;
    }

    public void setMouseMovedEvent(MouseEvent mouseMovedEvent) {
        this.mouseMovedEvent = mouseMovedEvent;
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

    public void useItem(Consumable item, int pos) {
        if (item != null) {
            item.consumeItem(this);
            inventory[pos] = null;
        }
    }
}
