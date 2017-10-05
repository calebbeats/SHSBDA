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

public class EnemyMissile extends GameFigure {

    // missile size
    private static final int SIZE = 10;
    private static final int MAX_EXPLOSION_SIZE = 3;
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame
    private int animationCheck=0;

    // public properties for quick access
    public Color color;
    public Point2D.Float target;

    private static final int UNIT_TRAVEL_DISTANCE = 4; // per frame move

    private int explosionCounter = 0;
    
    private Image launcherImage;
    private Image launcherImage2;
    private Image explosion1;
    private Image explosion2;
    private Image explosion3;
    

    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     */
    public EnemyMissile(float sx, float sy) {
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
        
        launcherImage = null;
        
        try {
           
            launcherImage = ImageIO.read(getClass().getResource("enemyMissile0.png"));
            launcherImage2 = ImageIO.read(getClass().getResource("enemyMissile1.png"));
            explosion1 = ImageIO.read(getClass().getResource("explosion0.png"));
            explosion2 = ImageIO.read(getClass().getResource("explosion1.png"));
            explosion3 = ImageIO.read(getClass().getResource("explosion2.png"));
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
            if(explosionCounter ==0)
            {
                 g.drawImage(explosion1, (int)super.x, (int)super.y, 
                30, 30, null);
            }
            if(explosionCounter ==1)
            {
                g.drawImage(explosion2, (int)super.x, (int)super.y, 
                30, 30, null);
            }
            if(explosionCounter ==2)
            {
                g.drawImage(explosion3, (int)super.x, (int)super.y, 
                30, 30, null);
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
        
        super.x += dx;
        super.y += dy;
    }

    public void updateSize() {
        explosionCounter++;
         
    }

    public void updateState() {
        if (state == STATE_ALIVE) {
            double distance = target.distance(super.x, super.y);
            boolean targetReached = distance <= 2.0;
            if (targetReached) {
                this.goNextState();
            }
        } else if (state == STATE_DYING) {
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
