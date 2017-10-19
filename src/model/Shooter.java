package model;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
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
import sun.util.calendar.CalendarUtils;

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

    //-------------------

    //Images for animations go below
    //----------------------------------
    private Map<String, List<Image>> playerSprites;
    private Image playerImage;
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
                //do some death animation that lasts deadTimer amt of time
                deadTimer++;
            } else {
                this.goNextState();
            }
        } else {
            
            if (health <= 0) {
                state = STATE_DYING;
            }
            velocitySprint = isSprint ? 2 : 0;

            moveX();
            moveY();

            calculateAngleOfView();

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
        Shooter shooterIntendedPossition = new Shooter((int) super.x + velocityX
                + Integer.signum(velocityX) * velocitySprint, (int) super.y);
//        Main.gameData.terrainFigures.forEach(terrain -> {
//            if (velocityX != 0 && !shooterIntendedPossition
//                    .getCollisionBox().intersects(terrain.getCollisionBox())) {
//                super.x += velocityX + Integer.signum(velocityX) * velocitySprint;
//                
//            }
//        });

        for (GameFigure t : Main.gameData.terrainFigures) {
            if (velocityX != 0 && !shooterIntendedPossition
                    .getCollisionBox().intersects(t.getCollisionBox())) {
                super.x += velocityX + Integer.signum(velocityX) * velocitySprint;

            } else {
                super.x -= 4 * (velocityX + Integer.signum(velocityX) * velocitySprint);
                return;
            }
        }
    }

    private void moveY() {
        // Allow player to move around terrain 
        // and disable sprint if move backward from mouse's position
        Shooter shooterIntendedPossition = new Shooter((int) super.x,
                (int) super.y + velocityY + Integer.signum(velocityY) * velocitySprint);
        
//        Main.gameData.terrainFigures.forEach(terrain -> {
//            if (velocityY != 0 && !shooterIntendedPossition
//                    .getCollisionBox().intersects(terrain.getCollisionBox())) {
//                super.y += velocityY + Integer.signum(velocityY) * velocitySprint;
//            }
//        });

        for (GameFigure t : Main.gameData.terrainFigures) {
            if (velocityY != 0 && !shooterIntendedPossition
                    .getCollisionBox().intersects(t.getCollisionBox())) {
                super.y += velocityY + Integer.signum(velocityY) * velocitySprint;

            } else {
                super.y -= 4 * (velocityY + Integer.signum(velocityY) * velocitySprint);
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
    
    public void testItem()
    {
        inventory[0] = new WeakPotion(1);
        inventory[1] = new MediumPotion(2);
        inventory[2] = new StrongPotion(3);
    }
}
