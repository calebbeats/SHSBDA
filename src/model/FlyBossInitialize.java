/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
//import static model.FlyEnemy.getImage;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DONE;
import static model.GameFigure.STATE_DYING;
import view.GamePanel;

/**
 *
 * @author dinhn
 */
public class FlyBossInitialize extends GameFigure implements FlyBoss {

    private final int WIDTH = 10;
    private final int HEIGHT = 25;
    //private final Color color = Color.red;
    private final int UNIT_TRAVEL = 2; // per frame

    private int dx = 3;
    private int dy = 3;
    private int direction = 1; // +1: move down; -1 move up
    private int directionX = 1; // +1: move down; -1 move up
    private int count = 0;
    private int maxcount = 30;
    private int timer = 0;

    private Image alive;
    private Image alive1;
    private Image alive2;
    private Image alive3;
    private Image death1;
    private Image death2;
    private Image death3;
    private int blast = 15;
    private long blast_stamp = 0;
    Image enemyImage;
    private int animationCheck = 0;
    private static int deathCounter = 0;
    public int size;
    private int maxHealth;
    private int health;
    private int deadTimer = 0;

    public FlyBossInitialize(float x, float y, int size) {
        super(x, y);
        this.size = size;
        super.state = STATE_ALIVE;

        alive = null;
        death1 = null;
        health = 250;
        maxHealth = health;

        try {
            alive = ImageIO.read(getClass().getResource("/resources/slimeboss.png"));
            alive1 = ImageIO.read(getClass().getResource("/resources/slimeboss.png"));
            alive2 = ImageIO.read(getClass().getResource("/resources/slimeboss.png"));
            alive3 = ImageIO.read(getClass().getResource("/resources/slimeboss.png"));
            //launcherImage = ImageIO.read(getClass().getResource("alien.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open enemy9.png");
            System.exit(-1);
        }
    }

    public static Image getImage(String fileName) {
        Image image = null;
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException ioe) {
            System.out.println("Error: Cannot open image:" + fileName);
            JOptionPane.showMessageDialog(null, "Error: Cannot open image:" + fileName);
        }
        return image;
    }

    @Override
    public void render(Graphics2D g) {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        if (state == STATE_ALIVE) {
            g.drawImage(alive, (int) super.x, (int) super.y,
                    60, 40, null);
            g.drawImage(alive1, (int) super.x, (int) super.y,
                    60, 40, null);
            g.drawImage(alive2, (int) super.x, (int) super.y,
                    60, 40, null);
            g.drawImage(alive3, (int) super.x, (int) super.y,
                    60, 40, null);
        }
        if (state == STATE_DYING) {
            if (blast > 0) {
                if ((System.currentTimeMillis() - blast_stamp) > 150) {
                    enemyImage = getImage(imagePath + separator + "images" + separator
                            + "explosion" + Integer.toString(blast) + ".png");
                    blast = blast - 1;
                    if (blast == 0) {
                        state = STATE_DONE;
                    }
                    blast_stamp = System.currentTimeMillis();
                }
            }
            g.drawImage(enemyImage, (int) x, (int) y, null);

        }

    }

    @Override
    public Rectangle2D.Double getCollisionBox() {
        return new Rectangle2D.Double((int) (super.x - WIDTH / 2), (int) (super.y - HEIGHT / 2), WIDTH * 0.9, HEIGHT * 0.9); // to be corrected
    }

    @Override
    public boolean Collides(GameFigure anotherGF) {
        Rectangle2D.Double thisObj = this.getCollisionBox();
        //anotherObj = anotherGF.
        return thisObj.intersects(anotherGF.getCollisionBox());

    }

    @Override
    public void update() {
        if(state == STATE_ALIVE){
        if (timer < 60 ) 
            {
                timer++;
            }
        }
        if (direction > 0) {
            // moving to the right
            super.y += UNIT_TRAVEL;
            if (super.y + HEIGHT / 2 > GamePanel.height) {
                direction = -1;
            }
        } else {
            // moving to the left
            super.y -= UNIT_TRAVEL;
            if (super.y - HEIGHT / 2 <= 0) {
                direction = 1;
            }
        }
        if (state == STATE_DYING) {
            deadTimer++;
            if (deadTimer > 15) {
                this.goNextState();
            }
        }
    }

    @Override
    public String getGFType() {
        return "Boss";
    }

    public void shoot() {
        System.out.println("Blink Mage Shoots");
    }

    @Override
    public void takeDamage(int i) {
        health = health - i;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

}
