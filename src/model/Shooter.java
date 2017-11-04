package model;

import controller.Main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private int lowHealthValue;
    int lowHealthCounter = 1;
    int lowHealthPhase = 1;
    int lowHealthMovement = 1;
    private static int weaponPower; //when we implement weapons use this to decide damage amount

    int tempCounter = 0;
    int tempRemovalCounter;

    //made static so shop can access invo
    public static Item[] inventory = new Item[4];
    public static Equipment[] equipment = new Equipment[3];

    //-----------------
    //test object
    //-------------------
    //-------------------
    //Images for animations go below
    //----------------------------------
    private Map<String, List<Image>> playerSprites;
    private Image playerImage;
    public static Missile rangedWeapon;
    public static Melee meleeWeapon;
    public WeaponComponent weapon;
    public boolean lowHealth;
    int deadTimer = 0;
    // ----------------------------------

    //Player Movement
    //-------------------
    private int velocityX, velocityY, velocitySprint;
    private boolean isSprint;
    private MouseEvent mouseMovedEvent;
    private float angleOfView;
    private int slidingVelocityX, slidingVelocityY;
    private boolean sliding = false;
    //-----------------

    public Shooter(int x, int y) {
        super(x, y);
        super.state = STATE_ALIVE;
        weapon = new BasicWeapon();
        health = 100;
        mana = 100;
        maxHealth = health;
        maxMana = mana;
        weaponPower = 1;
        lowHealthValue = 30;
        lowHealth = false;
//        inventory = new Item[4];

        // Tests for items and equipment
        // This gets added every time the shooter is created, so it slows game way down
        //---------------------------------------------------------------------
        //---------------------------------------------------------------------
        try {
            // Create HashMap that contains player sprites 
            // to update mouse and sprint movement animations
            playerSprites = new HashMap<String, List<Image>>() {
                {
                    put("Walk", new ArrayList<>(Arrays.asList(
                            ImageIO.read(getClass().getResource("/resources/WalkEast.png")),
                            ImageIO.read(getClass().getResource("/resources/WalkNorth.png")),
                            ImageIO.read(getClass().getResource("/resources/WalkWest.png")),
                            ImageIO.read(getClass().getResource("/resources/WalkSouth.png")))));
                    put("Sprint", new ArrayList<>(Arrays.asList(
                            ImageIO.read(getClass().getResource("/resources/SprintEast.png")),
                            ImageIO.read(getClass().getResource("/resources/SprintNorth.png")),
                            ImageIO.read(getClass().getResource("/resources/SprintWest.png")),
                            ImageIO.read(getClass().getResource("/resources/SprintSouth.png")))));
                }
            };
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open Player Image");
            System.exit(-1);
        }

    }

    @Override
    public void render(Graphics2D g) {
        // Update player's view based on angleOfView
        playerImage = isSprint && (velocityX != 0 || velocityY != 0)
                ? updatePlayerImage("Sprint")
                : updatePlayerImage("Walk");
        g.drawImage(playerImage, (int) super.x, (int) super.y, PLAYER_WIDTH, PLAYER_HEIGHT, null);

        if (lowHealth) {
            float thickness = lowHealthCounter;
            Stroke oldStroke = g.getStroke();
            g.setStroke(new BasicStroke(thickness));
            int alpha = 127; // 50% transparent
            Color myColor = new Color(255, 0, 0, alpha);
            g.setColor(myColor);
            g.drawRect(0, 0, Main.WIN_WIDTH - 8, Main.WIN_HEIGHT - 30);
            g.setStroke(oldStroke);
            if (lowHealthPhase == 1 && lowHealthMovement == 1) {
                lowHealthCounter++;
                if (lowHealthCounter == 40) {
                    lowHealthPhase = 2;
                }
            } else if (lowHealthPhase == 2 && lowHealthMovement == 1) {
                lowHealthCounter--;
                if (lowHealthCounter == 1) {
                    lowHealthPhase = 1;
                }
            }
            if (lowHealthMovement == 1) {
                lowHealthMovement = 0;
            } else {
                lowHealthMovement = 1;
            }
        }

        g.setColor(Color.red);
        g.fillRect(20, 490, health, 20);
        g.setColor(Color.white);
        g.drawRect(20, 490, maxHealth, 20);
        g.setColor(Color.blue);
        g.fillRect(20, 520, mana, 20);
        g.setColor(Color.white);
        g.drawRect(20, 520, maxMana, 20);
        g.drawRect(20, 460, 20, 20);
        g.drawRect(50, 460, 20, 20);
        g.drawRect(80, 460, 20, 20);
        g.drawRect(110, 460, 20, 20);
        for (int i = 0; i < 4; i++) {
            if (inventory[i] != null) {
                g.drawImage(inventory[i].getIcon(), 20 + i * 30, 460, 20, 20, null);
            }
        }
    }

    @Override
    public void update() {
        if (state == STATE_DYING) {
            if (deadTimer < 10) {
                //do some death animation that lasts deadTimer amt of time
                deadTimer++;
            } else {
                this.goNextState();
            }
        } else {

            if (health <= 0) {
                this.goNextState();
            }
            if (health <= lowHealthValue) {
                lowHealth = true;
            } else {
                lowHealth = false;
            }
            velocitySprint = isSprint ? 2 : 0;

            moveX();
            moveY();
            if (!sliding) {
                slidingVelocityX = velocityX;
                slidingVelocityY = velocityY;
            }
            calculateAngleOfView();

            if (mana < maxMana) {
                mana++;
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
            if (super.y >= Main.WIN_HEIGHT - PLAYER_HEIGHT - 50) {
                super.y = Main.WIN_HEIGHT - PLAYER_HEIGHT - 50;
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

    private Image updatePlayerImage(String movement) {
        if (angleOfView > 45 && angleOfView < 135) {
            return (Image) (playerSprites.get(movement)).get(1);
        } else if (angleOfView >= 135 && angleOfView <= 225) {
            return (Image) (playerSprites.get(movement)).get(2);
        } else if (angleOfView > 225 && angleOfView < 315) {
            return (Image) (playerSprites.get(movement)).get(3);
        } else {
            return (Image) (playerSprites.get(movement)).get(0);
        }
    }

    private void moveX() {
        // Allow player to move around terrain 
        // and disable sprint if move backward from mouse's position
        BasicCollisionBox shooterIntendedPossition = new BasicCollisionBox((int) super.x + velocityX
                + Integer.signum(velocityX) * velocitySprint, (int) super.y, PLAYER_HEIGHT, PLAYER_WIDTH);

        for (GameFigure t : Main.gameData.terrainFigures) {
            if (velocityX != 0 && !shooterIntendedPossition
                    .getCollisionBox().intersects(t.getCollisionBox())) {
                super.x += velocityX + Integer.signum(velocityX) * velocitySprint;
                slidingVelocityX = velocityX;
                sliding = false;
            } else if (velocityX != 0 && t instanceof BlockTerrain && shooterIntendedPossition
                    .getCollisionBox().intersects(t.getCollisionBox())) {
                super.x -= 4 * (velocityX + Integer.signum(velocityX) * velocitySprint);
                slidingVelocityX = velocityX;
                sliding = false;
                return;
            } else if (t instanceof IceTerrain && shooterIntendedPossition
                    .getCollisionBox().intersects(t.getCollisionBox())) {
                super.x += slidingVelocityX + Integer.signum(slidingVelocityX);
                sliding = true;
                return;
            }
        }
    }

    private void moveY() {
        // Allow player to move around terrain 
        // and disable sprint if move backward from mouse's position
        Shooter shooterIntendedPossition = new Shooter((int) super.x,
                (int) super.y + velocityY + Integer.signum(velocityY) * velocitySprint);

        for (GameFigure t : Main.gameData.terrainFigures) {
            if (velocityY != 0 && !shooterIntendedPossition
                    .getCollisionBox().intersects(t.getCollisionBox())) {
                super.y += velocityY + Integer.signum(velocityY) * velocitySprint;
                slidingVelocityY = velocityY;
                sliding = false;
            } else if (velocityY != 0 && t instanceof BlockTerrain && shooterIntendedPossition
                    .getCollisionBox().intersects(t.getCollisionBox())) {
                super.y -= 4 * (velocityY + Integer.signum(velocityY) * velocitySprint);
                slidingVelocityY = velocityY;
                sliding = false;
                return;
            } else if (t instanceof IceTerrain && shooterIntendedPossition
                    .getCollisionBox().intersects(t.getCollisionBox())) {
                super.y += slidingVelocityY + Integer.signum(slidingVelocityY);
                sliding = true;
                return;
            }
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

    //use these to determine and set weapon damage
    public void setWeaponPower(int w) {
        this.weaponPower = w;
    }

    public static int getWeaponPower() {
        return Shooter.weaponPower;
    }

    //Temporary method to test healing items.
    public void takeDamage(int i) {
        health = health - (i / 5);
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public int getSlidingVelocityX() {
        return slidingVelocityX;
    }

    public void setSlidingVelocityX(int slidingVelocityX) {
        this.slidingVelocityX = slidingVelocityX;
    }

    public int getSlidingVelocityY() {
        return slidingVelocityY;
    }

    public void setSlidingVelocityY(int slidingVelocityY) {
        this.slidingVelocityY = slidingVelocityY;
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

    public void equipRanged(Missile weap) {
        rangedWeapon = weap;
    }

    public void equipMelee(Melee weap) {
        meleeWeapon = weap;
    }

    public static Missile getRangedWeapon() {
        return rangedWeapon;
    }

    public static Melee getMeleeWeapon() {
        return meleeWeapon;
    }

    public Missile shootRanged(int x, int y, Missile mis) {
        mis = rangedWeapon;
        mis = new Missile(getXofMissileShoot(),
                getYofMissileShoot(),
                x, y);
        return mis;
    }

    public Melee shootMelee(int x, int y, Melee mis) {
        mis = meleeWeapon;
        mis = new Melee(getXofMissileShoot(),
                getYofMissileShoot(),
                x, y);
        return mis;
    }

    public void equipItem(Equipment e, int pos) {
        if (equipment[pos] == null) {
            equipment[pos] = e;
            e.attachAugment(this);
        }
    }

    public void unequipItem(int pos) {
        if (equipment[pos] != null) {
            Equipment e = equipment[pos];
            e.removeAugment(this);
            equipment[pos] = null;
        }
    }

    public void testItem() {
        System.out.println("Adding mana augment");
        inventory[0] = new DefensivePulse(1);
        inventory[1] = new FireNova(2);
        inventory[2] = new TeleportStone(3);

    }

    public void testRemoval() {
        System.out.println("Removing mana augment");
        unequipItem(tempRemovalCounter);
        tempRemovalCounter--;
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
