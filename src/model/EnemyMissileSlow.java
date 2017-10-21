/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;

/**
 *
 * @author Kodo
 */
public class EnemyMissileSlow extends GameFigure {
    
    //Size
    //------------------------------
    private static final int SIZE = 30;
    
    //Displace per Frame
    //------------------------------
    private float dx; 
    private float dy; 
    
    //Target X and Y
    //------------------------------
    private float ty;
    private float tx;
    
    //Missile Speed
    //------------------------------
    private static final int UNIT_TRAVEL_DISTANCE = 10;

    private int animationCheck=0;

    //Public Prop
    //------------------------------
    public Color color;
    public Point2D.Float target;

    private Image missile1;
    private Image missile2;

    public EnemyMissileSlow(float sx, float sy) {
        super(sx, sy);
        
        float tx = Main.gameData.shooter.x + 10;
        float ty = Main.gameData.shooter.y + 10;
        this.target = new Point2D.Float(tx, ty);
        this.color = color;
        
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
        
        missile1 = null;
        missile2 = null;
        
        try {
           
            missile1 = ImageIO.read(getClass().getResource("/resources/slowMissile.png"));
            missile2 = ImageIO.read(getClass().getResource("/resources/slowMissile2.png"));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
        
    }

    @Override
    public void render(Graphics2D g) {
        if(state == STATE_ALIVE)
        {
            if(animationCheck == 0){
                g.drawImage(missile1, (int)super.x, (int)super.y, 
                SIZE, SIZE, null);
                animationCheck = 1;
            }
            else{
                g.drawImage(missile2, (int)super.x, (int)super.y, 
                SIZE, SIZE, null);
                animationCheck = 0;
            }
        }
        if(state == STATE_DYING){

            //Create animation of player for "Slow Effect"
            //-----------------------------------
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
        super.x += dx;
        super.y += dy;
    }

    public void updateSize() {}

    public void updateState() {
        if (state == STATE_ALIVE) {
            double distance = target.distance(super.x, super.y);
            boolean targetReached = distance <= 2.0;
            if (targetReached || (super.x > Main.WIN_WIDTH || super.y > Main.WIN_HEIGHT)) {
                this.goNextState();
            }
        } else if (state == STATE_DYING) {
                this.goNextState();
        }
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
