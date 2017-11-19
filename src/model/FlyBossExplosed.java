/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;

/**
 *
 * @author dinhn
 */
public class FlyBossExplosed extends GameFigure implements FlyBoss {

    private final int WIDTH = 10;
    private final int HEIGHT = 25;
    //private final Color color = Color.red;
    private final int UNIT_TRAVEL = 5; // per frame

    private int dx = 3;
    private int dy = 3;
    private int direction = 1; // +1: move down; -1 move up
    private int directionX = 1; // +1: move down; -1 move up
    private int count = 0;
    private int maxcount = 30;

    private Image alive;
    private Image alive1;
    private Image alive2;
    private Image alive3;
    private Image death1;
    private Image death2;
    private Image death3;
    private int blast = 15;
    private long blast_stamp = 0;
    private Image enemyImage;
    private int animationCheck = 0;
    private static int deathCounter = 0;
    public int size;
    private int maxHealth;
    private int health;
    private int deadTimer = 0;

    public FlyBossExplosed(float x, float y, int size) {
        super(x, y);
        this.size = size;
        super.state = STATE_ALIVE;

        alive = null;
        death1 = null;
        health = 250;
        maxHealth = health;

        try {
            alive = ImageIO.read(getClass().getResource("/resources/enemy7.png"));
            alive1 = ImageIO.read(getClass().getResource("/resources/enemy7s.png"));
            alive2 = ImageIO.read(getClass().getResource("/resources/enemy5.png"));
            alive3 = ImageIO.read(getClass().getResource("/resources/enemy6.png"));
            //launcherImage = ImageIO.read(getClass().getResource("alien.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open enemy9.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void shoot() {

    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle2D.Double getCollisionBox() {
        return new Rectangle2D.Double((int) (super.x - WIDTH / 2), (int) (super.y - HEIGHT / 2), WIDTH * 0.9, HEIGHT * 0.9); // to be corrected
    }

    public boolean Collides(GameFigure anotherGF) {
        Rectangle2D.Double thisObj = this.getCollisionBox();
        //anotherObj = anotherGF.
        return thisObj.intersects(anotherGF.getCollisionBox());

    }

    @Override
    public String getGFType() {
        return "Boss";
    }

    @Override
    public void takeDamage(int i) {

    }

    @Override
    public int getHealth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setHealth(int health) {

    }

    @Override
    public int getMaxHealth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMaxHealth(int maxHealth) {

    }

}
