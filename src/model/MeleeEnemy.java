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
import static model.GameFigure.STATE_DONE;
import static model.GameFigure.STATE_DYING;

public class MeleeEnemy extends GameFigure {

    // missile size
    private static final int SIZE = 10;
    private static final int MAX_EXPLOSION_SIZE = 3;
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame
    private int animationCheck=0;
    private float ox;
    private float oy;
    private float ty;
    private float tx;

    // public properties for quick access
    public Point2D.Float target;

    private static final int UNIT_TRAVEL_DISTANCE = 1; // per frame move

    private int explosionCounter = 0;
    
    private Image launcherImage;
    private Image launcherImage2;
    
    //image attack
    //image death
    
    private Image death;

    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     */
    public MeleeEnemy(float sx, float sy) {
        super(sx, sy);
        
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
        
        launcherImage = null;
        
        try {
            //add enemy place holder.
            launcherImage = ImageIO.read(getClass().getResource("EnemyPlaceHolder.png"));
            launcherImage2 = ImageIO.read(getClass().getResource("EnemyPlaceHolderFlip.png"));
            death = ImageIO.read(getClass().getResource("EnemyDeathPlaceHolder.png"));

            
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
                g.drawImage(launcherImage, (int)super.x, (int)super.y, 
                30, 30, null);
                animationCheck = 1;
            }
            else{
                g.drawImage(launcherImage2, (int)super.x, (int)super.y, 
                30, 30, null);
                animationCheck = 0;
            }
        }
        if(state == STATE_DYING){

            g.drawImage(death, (int)super.x, (int)super.y, 
                30, 30, null);   
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

    public void updateSize() {
        explosionCounter++;
         
    }

    public void updateState() {
        if (state == STATE_ALIVE) {
            double distance = target.distance(super.x, super.y);
            boolean targetReached = distance <= 10.0;
            if (targetReached) {
                           
                //add melee attack image and code
                           
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
                       
            //add death image
            if (explosionCounter >= MAX_EXPLOSION_SIZE) {
                this.goNextState();
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x - SIZE , this.y - SIZE, SIZE * 0.9D, SIZE * 0.9D);
    }

    @Override
    public void shoot() {
       System.out.println("Enemy Missiles Shoots");
    }

}
