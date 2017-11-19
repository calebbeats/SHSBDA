/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.DifficultyManager;
import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;

public class SuicideEnemy extends GameFigure {

    // Size
    //------------------------------
    private static final int SIZE = 30;

    //MAX EXPLOSION SIZE
    //Explosion Counter
    //------------------------------
    private static final int MAX_EXPLOSION_SIZE = 50;
    private int explosionCounter = 0;

    //Movespeed
    //------------------------------
    private static final int UNIT_TRAVEL_DISTANCE = 2;

    //health
    private int health;
    private int maxHealth;

    //Displacement per Frame
    //------------------------------
    private float dx;
    private float dy;

    private float ox;
    private float oy;
    private float ty;
    private float tx;

    // Public -> Target
    //------------------------------
    public Point2D.Float target;

    //Sprite
    //------------------------------
    private Image right;
    private Image left;
    private Image back;
    private Image explosion1;
    private Image explosion2;
    private Image explosion3;

    public SuicideEnemy(float sx, float sy) {

        super(sx, sy);
        health = 1;
        maxHealth = health;

        //Damage
        //Go-To method @ Line 166
        DAMAGE = 250;

        tx = Main.gameData.shooter.x + 10;
        ty = Main.gameData.shooter.y + 10;
        this.target = new Point2D.Float(tx, ty);

        double angle = Math.atan2(Math.abs(ty - sy), Math.abs(tx - sx));
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));

        if (tx > sx && ty < sy) { // target is upper-right side
            dy = -dy; // dx > 0, dx < 0
        } else if (tx < sx && ty < sy) { // target is upper-left side
            dx = -dx;
            dy = -dy;
        } else if (tx < sx && ty > sy) { // target is lower-left side
            dx = -dx;
        } else { // target is lower-right side
            // dx > 0 , dy > 0
        }

        right = null;
        left = null;
        back = null;

        try {
            right = ImageIO.read(getClass().getResource("/resources/suicideRight.png"));
            left = ImageIO.read(getClass().getResource("/resources/suicideLeft.png"));
            back = ImageIO.read(getClass().getResource("/resources/suicideBack.png"));
            explosion1 = ImageIO.read(getClass().getResource("/resources/nuke1.png"));
            explosion2 = ImageIO.read(getClass().getResource("/resources/nuke2.png"));
            explosion3 = ImageIO.read(getClass().getResource("/resources/nuke3.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (state == STATE_ALIVE) {

            //Suicide Enemy Sprite
            //------------------------------
            g.drawImage(left, (int) super.x, (int) super.y,
                    SIZE, SIZE, null);

            /**
             * **********************************************
             * if(animationCheck == 0){ g.drawImage(right, (int)super.x,
             * (int)super.y, 30, 30, null); animationCheck = 1; }
             *
             * else{ g.drawImage(left, (int)super.x, (int)super.y, 30, 30,
             * null); animationCheck = 0; }
            ***********************************************
             */
        }

        if (state == STATE_DYING) {

            //Explosion 3 Part Animation
            //------------------------------
            if (explosionCounter < 9) {
                g.drawImage(explosion1, (int) super.x, (int) super.y, SIZE, SIZE, null);
            }
            if (explosionCounter > 8 && explosionCounter < 18) {
                g.drawImage(explosion2, (int) super.x, (int) super.y, SIZE, SIZE, null);
            }
            if (explosionCounter > 17 && explosionCounter < 27) {
                g.drawImage(explosion3, (int) super.x, (int) super.y, SIZE, SIZE, null);
            }
        }
    }

    @Override
    public void update() {
        updateState();
        if (state == STATE_ALIVE) {
            updateLocation();
        } else if (state == STATE_DYING) {
            updateSize();
        }
    }

    public void updateLocation() {
        BasicCollisionBox enemyToMove = new BasicCollisionBox(super.x + dx, super.y + dy, SIZE, SIZE);

        for (GameFigure t : Main.gameData.terrainFigures) {
            if (!(enemyToMove.getCollisionBox().intersects(t.getCollisionBox())) || t instanceof IceTerrain) {
                super.x += dx;
                super.y += dy;
            } else {

                tx = Main.gameData.shooter.x + 10;
                ty = Main.gameData.shooter.y + 10;
                System.out.println("Tx Ty" + tx + " " + ty);
                this.target = new Point2D.Float(tx, ty);

                double angle = Math.atan2(Math.abs(ty - super.y), Math.abs(tx - super.x));
                dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
                dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));

                if (tx > super.x && ty < super.y) { // target is upper-right side
                    dy = -dy; // dx > 0, dx < 0
                } else if (tx < super.x && ty < super.y) { // target is upper-left side
                    dx = -dx;
                    dy = -dy;
                } else if (tx < super.x && ty > super.y) { // target is lower-left side
                    dx = -dx;
                } else { // target is lower-right side

                }
                System.out.println("Dx Dy" + dx + " " + dy);

                enemyToMove = new BasicCollisionBox(super.x + dx, super.y, SIZE, SIZE);
                if (!(enemyToMove.getCollisionBox().intersects(t.getCollisionBox()))) {
                    super.x += dx;
                    super.y -= 2 * dy;
                } else {
                    enemyToMove = new BasicCollisionBox(super.x, super.y + dy, SIZE, SIZE);
                    if (!(enemyToMove.getCollisionBox().intersects(t.getCollisionBox()))) {
                        super.y += dy;
                        super.x -= 2 * dx;
                    }
                }

                return;
            }
            enemyToMove = new BasicCollisionBox(super.x + dx, super.y + dy, SIZE, SIZE);
        }
        enemyToMove = null;
    }

    public void updateSize() {
        explosionCounter++;
    }

    public void updateState() {
        if (state == STATE_ALIVE) {
            double distance = target.distance(super.x, super.y);
            boolean targetReached = distance <= 10.0;
            if (targetReached) {
                ox = tx;
                oy = ty;

                tx = Main.gameData.shooter.x + 10;
                ty = Main.gameData.shooter.y + 10;
                System.out.println("Tx Ty" + tx + " " + ty);
                this.target = new Point2D.Float(tx, ty);

                double angle = Math.atan2(Math.abs(ty - super.y), Math.abs(tx - super.x));
                dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
                dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));

                if (tx > super.x && ty < super.y) { // target is upper-right side
                    dy = -dy; // dx > 0, dx < 0
                } else if (tx < super.x && ty < super.y) { // target is upper-left side
                    dx = -dx;
                    dy = -dy;
                } else if (tx < super.x && ty > super.y) { // target is lower-left side
                    dx = -dx;
                } else { // target is lower-right side

                }
                System.out.println("Dx Dy" + dx + " " + dy);
            }
        } else if (state == STATE_DYING) {
            if (explosionCounter >= MAX_EXPLOSION_SIZE) {
                this.goNextState();
            }
        }
    }

    public static void dealDamage() {
        GameData.shooter.takeDamage(DAMAGE);
    }

    public void takeDamage(int i) {
        health = health - (int) (i * DifficultyManager
                .getShooterDamageMultiplier());
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, SIZE * 0.9D, SIZE * 0.9D);
    }

    @Override
    public void shoot() {
        System.out.println("Enemy Missiles Shoots");
    }
}
